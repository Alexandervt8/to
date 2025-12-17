package com.cafe.app;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class MainServer {

    private static final int SOCKET_PORT = 5000;
    private static final int HTTP_PORT = 8080;

    public static void main(String[] args) {

        try {
            // ===============================
            // INICIAR SERVIDOR HTTP
            // ===============================
            new Thread(HttpServerApp::iniciar).start();

            // ===============================
            // SERVIDOR SOCKET
            // ===============================
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
            String ipLocal = InetAddress.getLocalHost().getHostAddress();

            System.out.println("========================================");
            System.out.println("  SERVIDOR DE CAFETERÍA INICIADO");
            System.out.println("========================================");
            System.out.println(" IP Local       : " + ipLocal);
            System.out.println(" Puerto HTTP    : " + HTTP_PORT);
            System.out.println(" Puerto Socket  : " + SOCKET_PORT);
            System.out.println("========================================");
            System.out.println(" Esperando clientes...");
            System.out.println("========================================");

            // ===============================
            // LOOP PRINCIPAL
            // ===============================
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + socket.getInetAddress());
                    new Thread(new ClientHandler(socket)).start();
                } catch (Exception e) {
                    System.err.println("Error al aceptar cliente: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("Error crítico del servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
