package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Cliente;
import java.util.Scanner;

public class VistaCliente {

    private final PedidoClienteController controller = new PedidoClienteController();

    public void crearCliente(Scanner sc) {

        Cliente cliente = new Cliente();

        System.out.print("Nombre: ");
        cliente.setNombre(sc.nextLine());

        System.out.print("Documento (DNI/RUC): ");
        cliente.setDocumento(sc.nextLine());

        System.out.print("Email: ");
        cliente.setEmail(sc.nextLine());

        System.out.print("Teléfono: ");
        cliente.setTelefono(sc.nextLine());

        String respuesta = controller.enviarCliente(cliente);

        System.out.println("-----------------------------------");
        System.out.println("Respuesta del servidor:");
        System.out.println(respuesta);
        System.out.println("-----------------------------------");
    }
}
