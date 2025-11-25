package com.cafe.view;

import java.util.Scanner;

public class MenuPrincipal {

    private VistaPedido vistaPedido = new VistaPedido();
    private VistaCliente vistaCliente = new VistaCliente();

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("==============================");
            System.out.println("   CLIENTE - CAFETERÍA");
            System.out.println("==============================");
            System.out.println("1. Realizar Pedido");
            System.out.println("2. Crear Cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> vistaPedido.hacerPedido();
                case 2 -> vistaCliente.crearCliente();
            }

        } while (opcion != 0);

        System.out.println("Saliendo del cliente...");
    }
}
