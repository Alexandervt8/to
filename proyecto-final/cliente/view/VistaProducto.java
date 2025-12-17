package com.cafe.view;

import com.cafe.controller.PedidoClienteController;
import com.cafe.model.Producto;
import java.util.Scanner;

public class VistaProducto {

    private final PedidoClienteController controller = new PedidoClienteController();

    public void crearProducto(Scanner sc) {

        Producto p = new Producto();

        System.out.print("Nombre del producto: ");
        p.setNombre(sc.nextLine());

        System.out.print("Precio: ");
        p.setPrecio(sc.nextDouble());
        sc.nextLine();

        System.out.print("Tiempo de preparación (min): ");
        p.setTiempoPreparacion(sc.nextInt());
        sc.nextLine();

        System.out.print("ID Categoría: ");
        p.setCategoriaId(sc.nextInt());
        sc.nextLine();

        p.setActivo(true);

        String resp = controller.enviarProducto(p);

        System.out.println("-----------------------------------");
        System.out.println(resp);
        System.out.println("-----------------------------------");
    }
}
