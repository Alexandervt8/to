package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Cliente;

import java.util.Scanner;

public class VistaCliente {

    private PedidoClienteController controller = new PedidoClienteController();

    public void crearCliente() {

        Scanner sc = new Scanner(System.in);

        Cliente cliente = new Cliente();

        System.out.print("Nombre: ");
        cliente.setNombre(sc.nextLine());

        System.out.print("Email: ");
        cliente.setEmail(sc.nextLine());

        System.out.print("Teléfono: ");
        cliente.setTelefono(sc.nextLine());

        // Valores por defecto
        cliente.setActivo(true);
        cliente.setFechaRegistro("2025-01-01 12:00:00");

        String respuesta = controller.enviarCliente(cliente);

        System.out.println("-----------------------------------");
        System.out.println("Respuesta del servidor:");
        System.out.println(respuesta);
        System.out.println("-----------------------------------");
    }
}
