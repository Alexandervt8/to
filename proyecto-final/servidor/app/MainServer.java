package com.cafe.app;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class MainServer {

    public static void main(String[] args) {

        try {
            int PUERTO = 5000;

            ServerSocket server = new ServerSocket(PUERTO);
            String ipLocal = InetAddress.getLocalHost().getHostAddress();

            System.out.println("========================================");
            System.out.println(" SERVIDOR DE CAFETERIA HÍBRIDA");
            System.out.println(" IP Local: " + ipLocal);
            System.out.println(" Puerto: " + PUERTO);
            System.out.println(" Esperando clientes...");
            System.out.println("========================================");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                new Thread(new ClientHandler(socket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
