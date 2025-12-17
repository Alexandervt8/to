package com.cafe.controller;

import com.cafe.model.Pedido;
import com.cafe.model.Cliente;
import com.cafe.model.Producto;
import com.cafe.model.Usuario;
import com.cafe.model.Categoria;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PedidoClienteController {

    // ===============================
    // CONFIGURACIÓN DEL SERVIDOR
    // ===============================
    private static final String SERVER_IP = "192.168.0.7"; // IP del servidor
    private static final int SERVER_PORT = 5000;

    // ===============================
    // MÉTODOS PÚBLICOS
    // ===============================
    public String enviarPedido(Pedido pedido) {
        if (pedido == null) return "Pedido inválido";
        return enviarObjeto(pedido);
    }

    public String enviarCliente(Cliente cliente) {
        if (cliente == null) return "Cliente inválido";
        return enviarObjeto(cliente);
    }

    public String enviarProducto(Producto producto) {
        if (producto == null) return "Producto inválido";
        return enviarObjeto(producto);
    }

    public String enviarUsuario(Usuario usuario) {
        if (usuario == null) return "Usuario inválido";
        return enviarObjeto(usuario);
    }
    public String enviarCategoria(Categoria categoria) {
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            return "Categoría inválida";
        }
        return enviarObjeto(categoria);
    }


    // ===============================
    // SOCKET GENÉRICO
    // ===============================
    private String enviarObjeto(Object objeto) {

        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Enviar objeto
            out.writeObject(objeto);
            out.flush();

            // Recibir respuesta
            Object respuesta = in.readObject();
            return respuesta != null ? respuesta.toString() : "Sin respuesta del servidor";

        } catch (Exception e) {
            return "Error conectando con el servidor: " + e.getMessage();
        }
    }
}
