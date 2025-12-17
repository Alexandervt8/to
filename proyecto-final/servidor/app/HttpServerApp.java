package com.cafe.app;

import com.cafe.dao.ClienteDAO;
import com.cafe.dao.ProductoDAO;
import com.cafe.controller.PedidoController;
import com.cafe.model.Cliente;
import com.cafe.model.Producto;
import com.cafe.model.Pedido;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HttpServerApp {

    private static final int PUERTO = 8080;
    private static final Gson gson = new Gson();

    // carpeta web RELATIVA al proyecto
    private static final String WEB_ROOT = "../web";

    public static void iniciar() {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PUERTO), 0);

            System.out.println("========================================");
            System.out.println(" SERVIDOR HTTP INICIADO");
            System.out.println(" URL: http://localhost:" + PUERTO);
            System.out.println(" Carpeta web: " + WEB_ROOT);
            System.out.println("========================================");

            // ==================================================
            // ARCHIVOS ESTÁTICOS
            // ==================================================
            server.createContext("/", exchange -> {
                try {
                    String path = exchange.getRequestURI().getPath();
                    if (path.equals("/")) path = "/index.html";

                    Path file = Paths.get(WEB_ROOT + path);

                    if (!Files.exists(file)) {
                        enviar(exchange, 404, "404 - Archivo no encontrado");
                        return;
                    }

                    byte[] data = Files.readAllBytes(file);
                    exchange.getResponseHeaders().add("Content-Type", mime(path));
                    exchange.sendResponseHeaders(200, data.length);
                    exchange.getResponseBody().write(data);
                    exchange.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // ==================================================
            // API: LISTAR PRODUCTOS
            // ==================================================
            server.createContext("/api/productos", exchange -> {
                cors(exchange);

                try {
                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                        ProductoDAO dao = new ProductoDAO();
                        List<Producto> productos = dao.listarActivos();
                        enviarJson(exchange, productos);
                    } else {
                        enviar(exchange, 405, "Método no permitido");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    enviar(exchange, 500, "Error interno al listar productos");
                }
            });

            // ==================================================
            // API: REGISTRAR CLIENTE
            // ==================================================
            server.createContext("/cliente/registrar", exchange -> {
                cors(exchange);

                try {
                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                        Cliente cliente = gson.fromJson(
                                new String(exchange.getRequestBody().readAllBytes()),
                                Cliente.class
                        );

                        ClienteDAO dao = new ClienteDAO();
                        int id = dao.create(cliente);

                        if (id > 0) {
                            enviar(exchange, 200, "Cliente registrado con ID: " + id);
                        } else {
                            enviar(exchange, 500, "Error al registrar cliente");
                        }

                    } else {
                        enviar(exchange, 405, "Método no permitido");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    enviar(exchange, 500, "Error interno al registrar cliente");
                }
            });

            // ==================================================
            // API: REALIZAR PEDIDO
            // ==================================================
            server.createContext("/api/realizar-pedido", exchange -> {
                cors(exchange);

                try {
                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

                        Pedido pedido = gson.fromJson(
                                new String(exchange.getRequestBody().readAllBytes()),
                                Pedido.class
                        );

                        PedidoController controller = new PedidoController();
                        int idPedido = controller.registrarPedido(pedido);

                        if (idPedido > 0) {
                            enviar(exchange, 200,
                                    "Pedido registrado correctamente. ID: " + idPedido);
                        } else {
                            enviar(exchange, 500,
                                    "Error al registrar el pedido");
                        }

                    } else {
                        enviar(exchange, 405, "Método no permitido");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    enviar(exchange, 500, "Error interno al registrar pedido");
                }
            });

            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================================================
    // UTILIDADES (SIN throws → compatibles con lambdas)
    // ==================================================
    private static void cors(HttpExchange ex) {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void enviar(HttpExchange ex, int code, String msg) {
        try {
            byte[] data = msg.getBytes();
            ex.sendResponseHeaders(code, data.length);
            ex.getResponseBody().write(data);
            ex.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviarJson(HttpExchange ex, Object obj) {
        try {
            byte[] data = gson.toJson(obj).getBytes();
            ex.getResponseHeaders().add("Content-Type", "application/json");
            ex.sendResponseHeaders(200, data.length);
            ex.getResponseBody().write(data);
            ex.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String mime(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }
}
