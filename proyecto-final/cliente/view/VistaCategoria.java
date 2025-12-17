package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Categoria;

import java.util.Scanner;

public class VistaCategoria {

    private final PedidoClienteController controller = new PedidoClienteController();

    public void crearCategoria(Scanner sc) {

        Categoria c = new Categoria();

        System.out.print("Nombre de la categoría: ");
        c.setNombre(sc.nextLine());

        String respuesta = controller.enviarCategoria(c);

        System.out.println("-----------------------------------");
        System.out.println(respuesta);
        System.out.println("-----------------------------------");
    }
}
