package com.cafe.controller;

import com.cafe.model.Pedido;
import com.cafe.model.Cliente;
import com.cafe.model.Producto;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PedidoClienteController {

    private String serverIp = "localhost";
    private int serverPort = 5001; // Asegúrate de que coincida con tu MainServer

    // Método existente
    public String enviarPedido(Pedido pedido) {
        try (Socket socket = new Socket(serverIp, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
             
            out.writeObject(pedido);
            return (String) in.readObject();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Método existente
    public String enviarCliente(Cliente cliente) {
        try (Socket socket = new Socket(serverIp, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
             
            out.writeObject(cliente);
            return (String) in.readObject();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // --- NUEVO MÉTODO PARA EL FRONTEND ---
    @SuppressWarnings("unchecked")
    public List<Producto> listarProductos() {
        try (Socket socket = new Socket(serverIp, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            // Enviamos un comando o solicitud especial. 
            // IMPORTANTE: Tu MainServer debe saber interpretar este String "LISTAR_PRODUCTOS".
            // Si tu servidor falla aquí, es porque espera siempre un objeto Pedido o Cliente.
            // Una solución rápida si no quieres tocar el backend complejo es envolver esto.
            out.writeObject("LISTAR_PRODUCTOS"); 

            Object response = in.readObject();
            if (response instanceof List) {
                return (List<Producto>) response;
            } else {
                System.out.println("El servidor no devolvió una lista: " + response);
                return new ArrayList<>();
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}