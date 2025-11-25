package com.cafe.controller;

import com.cafe.model.Pedido;
import com.cafe.model.Cliente;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PedidoClienteController {

    private String serverIp = "192.168.0.7"; // <- CAMBIAR A LA IP LOCAL DEL SERVIDOR
    private int serverPort = 5000;

    public String enviarPedido(Pedido pedido) {

        try {
            Socket socket = new Socket(serverIp, serverPort);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // enviar pedido
            out.writeObject(pedido);

            // recibir respuesta
            String respuesta = (String) in.readObject();

            socket.close();
            return respuesta;

        } catch (Exception e) {
            return "Error conectando con el servidor: " + e.getMessage();
        }
    }

    public String enviarCliente(Cliente cliente) {

        try {
            Socket socket = new Socket(serverIp, serverPort);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(cliente);   // enviamos Cliente

            String respuesta = (String) in.readObject();

            socket.close();
            return respuesta;

        } catch (Exception e) {
            return "Error conectando con el servidor: " + e.getMessage();
        }
    }

}
