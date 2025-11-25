package com.cafe.app;

import com.cafe.controller.PedidoController;
import com.cafe.dao.ClienteDAO;
import com.cafe.model.Cliente;
import com.cafe.model.Pedido;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

            if (recibido instanceof Pedido pedido) {
                System.out.println("Pedido recibido del cliente.");

                PedidoController controller = new PedidoController();
                boolean ok = controller.registrarPedido(pedido);

                out.writeObject(ok ? "Pedido registrado correctamente" : "Error al guardar pedido");
            }
            else if (recibido instanceof Cliente cliente) {
                System.out.println("Cliente recibido.");

                ClienteDAO dao = new ClienteDAO();
                int id = dao.create(cliente);

                if (id > 0)
                    out.writeObject("Cliente registrado con ID: " + id);
                else
                    out.writeObject("Error al crear cliente");
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
