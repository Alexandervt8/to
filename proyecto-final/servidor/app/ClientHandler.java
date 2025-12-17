package com.cafe.app;

import com.cafe.controller.PedidoController;
import com.cafe.dao.ClienteDAO;
import com.cafe.model.Cliente;
import com.cafe.model.Pedido;
import com.cafe.model.Producto;
import com.cafe.dao.ProductoDAO;
import com.cafe.model.Usuario;
import com.cafe.dao.UsuarioDAO;
import com.cafe.model.Categoria;
import com.cafe.dao.CategoriaDAO;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            Object recibido = in.readObject();

            // ===============================
            // PEDIDO
            // ===============================
            if (recibido instanceof Pedido pedido) {

                System.out.println("Pedido recibido por socket.");

                PedidoController controller = new PedidoController();
                int idPedido = controller.registrarPedido(pedido);

                if (idPedido > 0) {
                    out.writeObject("Pedido registrado correctamente. ID: " + idPedido);
                } else {
                    out.writeObject("Error al registrar el pedido");
                }
            }

            // ===============================
            // CLIENTE
            // ===============================
            else if (recibido instanceof Cliente cliente) {

                System.out.println("Cliente recibido por socket.");

                ClienteDAO dao = new ClienteDAO();
                int id = dao.create(cliente);

                if (id > 0) {
                    out.writeObject("Cliente registrado con ID: " + id);
                } else {
                    out.writeObject("Error al registrar cliente");
                }
            }

            // ===============================
            // PRODUCTO
            // ===============================
            else if (recibido instanceof Producto producto) {
                ProductoDAO dao = new ProductoDAO();
                int id = dao.crear(producto);
                out.writeObject(id > 0
                    ? "Producto creado con ID: " + id
                    : "Error al crear producto");
            }

            // ===============================
            // USUARIO
            // ===============================
            else if (recibido instanceof Usuario usuario) {
                UsuarioDAO dao = new UsuarioDAO();
                int id = dao.crear(usuario);
                out.writeObject(id > 0
                    ? "Usuario creado con ID: " + id
                    : "Error al crear usuario");
            }

            // ===============================
            // CATEGORIA
            // ===============================
            else if (recibido instanceof Categoria categoria) {

                CategoriaDAO dao = new CategoriaDAO();
                int id = dao.crear(categoria);

                out.writeObject(
                        id > 0
                                ? "Categoría creada con ID: " + id
                                : "Error al crear categoría"
                );
            }

            // ===============================
            // OBJETO DESCONOCIDO
            // ===============================
            else {
                out.writeObject("Objeto no reconocido por el servidor");
            }

            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject("Error interno del servidor: " + e.getMessage());
                out.flush();
            } catch (Exception ignored) {}
        } finally {
            try {
                socket.close();
            } catch (Exception ignored) {}
        }
    }
}
