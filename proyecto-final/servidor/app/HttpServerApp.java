package com.cafe.app;

import com.cafe.dao.ClienteDAO;
import com.cafe.model.Cliente;
import com.google.gson.Gson;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class HttpServerApp {

    private static final int PUERTO = 8080;
    private static final Gson gson = new Gson();

    // Ruta de tu carpeta web física
    private static final String WEB_ROOT = "E:/to/pedidos/web";

    public static void iniciar() {
        try {

            HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PUERTO), 0);

            System.out.println("========================================");
            System.out.println(" SERVIDOR HTTP INICIADO");
            System.out.println(" URL pública: http://" 
                + java.net.InetAddress.getLocalHost().getHostAddress() 
                + ":" + PUERTO);
            System.out.println(" Carpeta web: " + WEB_ROOT);
            System.out.println("========================================");

            // ==============================
            //  SERVIR ARCHIVOS ESTÁTICOS
            // ==============================
            server.createContext("/", exchange -> {
                try {
                    String path = exchange.getRequestURI().getPath();

                    // Página principal
                    if (path.equals("/")) {
                        path = "/reservation.html";
                    }

                    String filePath = WEB_ROOT + path;
                    java.nio.file.Path file = Paths.get(filePath);

                    if (!Files.exists(file)) {
                        String resp = "404 - Archivo no encontrado: " + path;
                        exchange.sendResponseHeaders(404, resp.length());
                        exchange.getResponseBody().write(resp.getBytes());
                        exchange.close();
                        return;
                    }

                    byte[] bytes = Files.readAllBytes(file);

                    exchange.getResponseHeaders()
                            .add("Content-Type", detectarTipo(path));
                    exchange.sendResponseHeaders(200, bytes.length);
                    exchange.getResponseBody().write(bytes);
                    exchange.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // ==============================
            //  API: REGISTRAR CLIENTE
            // ==============================
            server.createContext("/cliente/registrar", (HttpExchange exchange) -> {

                // CORS
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                if ("POST".equals(exchange.getRequestMethod())) {
                    try {

                        String body = new String(exchange.getRequestBody().readAllBytes());

                        Cliente cliente = gson.fromJson(body, Cliente.class);
                        ClienteDAO dao = new ClienteDAO();

                        int id = dao.create(cliente);

                        String respuesta = (id > 0)
                                ? "Cliente registrado con ID: " + id
                                : "Error al registrar cliente";

                        enviarRespuesta(exchange, respuesta);

                    } catch (SQLException e) {
                        e.printStackTrace();
                        enviarRespuesta(exchange, "Error SQL al registrar cliente: " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        enviarRespuesta(exchange, "Error al procesar cliente: " + e.getMessage());
                    }
                } else {
                    enviarRespuesta(exchange, "Método no permitido.");
                }
            });

            // Iniciar servidor
            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Detectar tipo MIME para cada archivo
    private static String detectarTipo(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".ico")) return "image/x-icon";
        return "application/octet-stream";
    }

    // Respuestas de API
    private static void enviarRespuesta(HttpExchange exchange, String resp) {
        try {
            byte[] data = resp.getBytes();
            exchange.sendResponseHeaders(200, data.length);
            OutputStream os = exchange.getResponseBody();
            os.write(data);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
