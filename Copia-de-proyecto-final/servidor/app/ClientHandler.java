package com.cafe.app;

import com.cafe.controller.PedidoController;
import com.cafe.dao.ClienteDAO;
import com.cafe.dao.ProductoDAO; 
import com.cafe.model.Cliente;
import com.cafe.model.Pedido;
import com.cafe.model.Producto;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            Object recibido = in.readObject();

            // 1. Si es un Pedido
            if (recibido instanceof Pedido pedido) {
                System.out.println("Pedido recibido.");
                PedidoController controller = new PedidoController();
                boolean ok = controller.registrarPedido(pedido);
                out.writeObject(ok ? "Pedido registrado correctamente" : "Error al guardar pedido");
            } 
            // 2. Si es un Cliente (Registro)
            else if (recibido instanceof Cliente cliente) {
                System.out.println("Cliente nuevo recibido.");
                ClienteDAO dao = new ClienteDAO();
                int id = dao.create(cliente);
                out.writeObject(id > 0 ? "Cliente registrado con ID: " + id : "Error al crear cliente");
            } 
            // 3. Si es una Solicitud de Texto (Frontend) <-- ESTA ES LA PARTE QUE FALTABA
            else if (recibido instanceof String solicitud) {
                if (solicitud.equals("LISTAR_PRODUCTOS")) {
                    System.out.println("Enviando lista de productos...");
                    ProductoDAO productoDAO = new ProductoDAO();
                    // IMPORTANTE: Asegúrate de que tu ProductoDAO tenga un método listar()
                    List<Producto> productos = productoDAO.listarActivos(); 
                    out.writeObject(productos);
                }
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}