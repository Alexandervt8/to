package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Pedido;
import com.cafe.model.PedidoItem;
import java.util.Scanner;

public class VistaPedido {

    private final PedidoClienteController controller = new PedidoClienteController();

    public void hacerPedido(Scanner sc) {

        Pedido pedido = new Pedido();

        System.out.print("ID del cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        pedido.setClienteId(idCliente);
        pedido.setTipo("mesa");
        pedido.setEstado("abierto");

        System.out.println("Ingrese los ítems del pedido");
        System.out.println("(0 para finalizar)");

        while (true) {
            System.out.print("ID del producto: ");
            int productoId = sc.nextInt();
            sc.nextLine();

            if (productoId == 0) break;

            System.out.print("Cantidad: ");
            int cantidad = sc.nextInt();
            sc.nextLine();

            PedidoItem item = new PedidoItem();
            item.setProductoId(productoId);
            item.setCantidad(cantidad);
            item.setEstado("pendiente");

            pedido.addItem(item);
        }

        String respuesta = controller.enviarPedido(pedido);

        System.out.println("-----------------------------------");
        System.out.println("Respuesta del servidor:");
        System.out.println(respuesta);
        System.out.println("-----------------------------------");
    }
}
