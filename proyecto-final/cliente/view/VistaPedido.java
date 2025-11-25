package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Pedido;
import com.cafe.model.PedidoItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VistaPedido {

    private PedidoClienteController controller = new PedidoClienteController();

    public void hacerPedido() {

        Scanner sc = new Scanner(System.in);

        Pedido pedido = new Pedido();

        // ID del cliente
        System.out.print("ID del cliente: ");
        int idCliente = sc.nextInt();
        pedido.setClienteId(idCliente);

        // CAMPOS OBLIGATORIOS PARA EVITAR NULL
        pedido.setEstado(Pedido.Estado.PENDIENTE);         // Valor por defecto
        pedido.setMetodoPago(Pedido.MetodoPago.EFECTIVO);  // Valor por defecto

        // Fecha actual (formato compatible con tu BD)
        String fechaActual = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        pedido.setFecha(fechaActual);

        double total = 0;

        System.out.println("Ingrese los ítems del pedido");
        System.out.println("(0 para finalizar)");

        while (true) {
            System.out.print("ID del producto: ");
            int productoId = sc.nextInt();

            if (productoId == 0)
                break;

            System.out.print("Cantidad: ");
            int cant = sc.nextInt();

            System.out.print("Precio unitario: ");
            double precio = sc.nextDouble();

            PedidoItem item = new PedidoItem(0, productoId, cant, precio);

            pedido.addItem(item);

            // acumular total
            total += (cant * precio);
        }

        // Asignar total calculado
        pedido.setTotal(total);

        // Enviar al servidor
        String respuesta = controller.enviarPedido(pedido);

        System.out.println("-----------------------------------");
        System.out.println("Respuesta del servidor:");
        System.out.println(respuesta);
        System.out.println("-----------------------------------");
    }
}
