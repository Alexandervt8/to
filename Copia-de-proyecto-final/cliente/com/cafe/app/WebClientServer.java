package com.cafe.app;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Cliente;
import com.cafe.model.Pedido;
import com.cafe.model.PedidoItem;
import com.cafe.model.Producto;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WebClientServer {

    private static final int PORT = 8080;
    private static final String TEMPLATE_DIR = "coffee-shop-html-template"; 

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Rutas
        server.createContext("/", new StaticFileHandler());
        server.createContext("/api/productos", new ProductosHandler());
        server.createContext("/api/crear-cliente", new ClienteHandler());
        
        // NUEVA RUTA: Procesar Pedido
        server.createContext("/api/realizar-pedido", new PedidoWebHandler());

        server.setExecutor(null);
        System.out.println("Cliente Web corriendo en: http://localhost:" + PORT);
        server.start();
    }

    // --- MANEJADORES ---

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            File file = new File(TEMPLATE_DIR + path);
            if (!file.exists()) file = new File("../" + TEMPLATE_DIR + path);

            if (file.exists()) {
                String mimeType = Files.probeContentType(file.toPath());
                if (path.endsWith(".css")) mimeType = "text/css";
                if (path.endsWith(".js")) mimeType = "application/javascript";
                exchange.getResponseHeaders().set("Content-Type", mimeType);
                exchange.sendResponseHeaders(200, file.length());
                try (OutputStream os = exchange.getResponseBody(); FileInputStream fs = new FileInputStream(file)) {
                    fs.transferTo(os);
                }
            } else {
                String msg = "404 Not Found";
                exchange.sendResponseHeaders(404, msg.length());
                try (OutputStream os = exchange.getResponseBody()) { os.write(msg.getBytes()); }
            }
        }
    }

    static class ProductosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            PedidoClienteController controller = new PedidoClienteController();
            List<Producto> lista = controller.listarProductos();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                Producto p = lista.get(i);
                json.append(String.format("{\"id\":%d, \"nombre\":\"%s\", \"precio\":%.2f, \"categoriaId\":%d}",
                        p.getId(), p.getNombre(), p.getPrecio(), p.getCategoriaId()));
                if (i < lista.size() - 1) json.append(",");
            }
            json.append("]");

            byte[] resp = json.toString().getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, resp.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(resp); }
        }
    }

    static class ClienteHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"))
                        .lines().collect(Collectors.joining());
                
                Cliente c = new Cliente();
                c.setNombre(extractJsonValue(body, "nombre"));
                c.setEmail(extractJsonValue(body, "email"));
                c.setTelefono(extractJsonValue(body, "telefono"));
                c.setActivo(true);
                c.setFechaRegistro("2025-01-01");

                PedidoClienteController controller = new PedidoClienteController();
                String respuesta = controller.enviarCliente(c);

                byte[] respBytes = respuesta.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, respBytes.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(respBytes); }
            }
        }
    }

    // --- HANDLER DE PEDIDOS SIMPLIFICADO Y ROBUSTO ---
    static class PedidoWebHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Cabeceras para evitar bloqueos del navegador
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    // 1. Leer el texto enviado por el navegador
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String body = br.lines().collect(Collectors.joining());

                    System.out.println("JSON Recibido: " + body); // Ver en consola qué llega

                    // 2. Extraer datos principales
                    String idStr = extractJsonNumber(body, "clienteId");
                    String totalStr = extractJsonNumber(body, "total");

                    if (idStr.equals("0") || idStr.isEmpty()) {
                        System.err.println("Error: ID Cliente inválido");
                        enviarRespuesta(exchange, 400, "Error: ID Cliente invalido");
                        return;
                    }

                    Pedido pedido = new Pedido();
                    pedido.setClienteId(Integer.parseInt(idStr));
                    pedido.setTotal(Double.parseDouble(totalStr));
                    pedido.setEstado(Pedido.Estado.PENDIENTE);
                    pedido.setMetodoPago(Pedido.MetodoPago.EFECTIVO);
                    pedido.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    // 3. Extraer Items (Método de búsqueda simple)
                    // Buscamos iterativamente en el texto para evitar errores de formato
                    String tempBody = body; 
                    while (tempBody.contains("\"productoId\":")) {
                        // Extraer valores del primer item que aparezca en tempBody
                        String pId = extractJsonNumber(tempBody, "productoId");
                        String cant = extractJsonNumber(tempBody, "cantidad");
                        String prec = extractJsonNumber(tempBody, "precio");

                        if (!pId.equals("0")) {
                            PedidoItem item = new PedidoItem(0, 
                                    Integer.parseInt(pId), 
                                    Integer.parseInt(cant), 
                                    Double.parseDouble(prec));
                            pedido.addItem(item);
                        }

                        // Cortamos el texto para buscar el siguiente item
                        int nextIndex = tempBody.indexOf("\"productoId\":") + 13;
                        tempBody = tempBody.substring(nextIndex);
                    }

                    System.out.println("Procesando pedido para Cliente ID: " + pedido.getClienteId() + " con " + pedido.getItems().size() + " items.");

                    // 4. Enviar al Servidor Principal (Backend)
                    PedidoClienteController controller = new PedidoClienteController();
                    String respuestaServer = controller.enviarPedido(pedido);
                    System.out.println("Respuesta del Backend: " + respuestaServer);

                    // 5. Responder al navegador
                    enviarRespuesta(exchange, 200, respuestaServer);

                } catch (Exception e) {
                    // Si algo falla, IMPRIMIR EL ERROR para poder verlo
                    e.printStackTrace();
                    enviarRespuesta(exchange, 500, "Error interno en el servidor Web: " + e.getMessage());
                }
            } else {
                exchange.sendResponseHeaders(204, -1); // Para OPTIONS u otros
            }
        }

        private void enviarRespuesta(HttpExchange exchange, int codigo, String respuesta) throws IOException {
            byte[] bytes = respuesta.getBytes("UTF-8");
            exchange.sendResponseHeaders(codigo, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // Helpers simples para evitar librerías externas (GSON/Jackson)
    private static String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) return "";
        start += pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static String extractJsonNumber(String json, String key) {
        // Busca "key": 123  o "key":123.45
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return "0";
        start += pattern.length();
        
        // Encontrar el fin del número (coma o llave de cierre)
        int endComma = json.indexOf(",", start);
        int endBrace = json.indexOf("}", start);
        int endBracket = json.indexOf("]", start);
        
        int end = endComma;
        if (end == -1 || (endBrace != -1 && endBrace < end)) end = endBrace;
        if (end == -1 || (endBracket != -1 && endBracket < end)) end = endBracket;
        
        return json.substring(start, end).trim();
    }
}