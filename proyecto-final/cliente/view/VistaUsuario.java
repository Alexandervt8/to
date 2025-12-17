package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Usuario;
import java.util.Scanner;

public class VistaUsuario {

    private final PedidoClienteController controller = new PedidoClienteController();

    public void crearUsuario(Scanner sc) {

        Usuario u = new Usuario();

        System.out.print("Nombre: ");
        u.setNombre(sc.nextLine());

        System.out.print("Usuario: ");
        u.setUsuario(sc.nextLine());

        System.out.print("Password: ");
        u.setPassword(sc.nextLine());

        System.out.print("Rol (admin/cajero/cocina): ");
        u.setRol(sc.nextLine());

        String resp = controller.enviarUsuario(u);

        System.out.println("-----------------------------------");
        System.out.println(resp);
        System.out.println("-----------------------------------");
    }
}
