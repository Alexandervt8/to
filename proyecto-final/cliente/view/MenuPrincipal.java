package com.cafe.view;

import java.util.Scanner;

public class MenuPrincipal {

    private final VistaPedido vistaPedido = new VistaPedido();
    private final VistaCliente vistaCliente = new VistaCliente();
    private final VistaProducto vistaProducto = new VistaProducto();
    private final VistaUsuario vistaUsuario = new VistaUsuario();
    private final VistaCategoria vistaCategoria = new VistaCategoria();

    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {

        int opcion = -1; // 🔥 inicializado

        do {
            System.out.println("==============================");
            System.out.println("   CLIENTE - CAFETERÍA");
            System.out.println("==============================");
            System.out.println("1. Realizar Pedido");
            System.out.println("2. Crear Cliente");
            System.out.println("3. Crear Producto");
            System.out.println("4. Crear Usuario");
            System.out.println("5. Crear Categoría");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            if (!sc.hasNextInt()) {
                sc.nextLine(); // limpiar entrada inválida
                System.out.println("Opción inválida");
                continue;
            }

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> vistaPedido.hacerPedido(sc);
                case 2 -> vistaCliente.crearCliente(sc);
                case 3 -> vistaProducto.crearProducto(sc);
                case 4 -> vistaUsuario.crearUsuario(sc);
                case 5 -> vistaCategoria.crearCategoria(sc);

                case 0 -> System.out.println("Saliendo del cliente...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }
}
