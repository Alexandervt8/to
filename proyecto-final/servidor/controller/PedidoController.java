package com.cafe.controller;

import com.cafe.dao.PedidoDAO;
import com.cafe.model.Pedido;

public class PedidoController {

    public int registrarPedido(Pedido pedido) {

        try {
            // ===============================
            // VALIDACIONES BÁSICAS
            // ===============================
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido nulo");
            }

            if (pedido.getClienteId() <= 0) {
                throw new IllegalArgumentException("Cliente inválido");
            }

            if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
                throw new IllegalArgumentException("Pedido sin items");
            }

            // ===============================
            // VALORES POR DEFECTO
            // ===============================
            if (pedido.getTipo() == null) {
                pedido.setTipo("mesa");
            }

            if (pedido.getEstado() == null) {
                pedido.setEstado("abierto");
            }

            // ===============================
            // PERSISTENCIA
            // ===============================
            PedidoDAO dao = new PedidoDAO();
            return dao.crearPedidoConItems(pedido);

        } catch (Exception e) {
            System.err.println("Error en PedidoController: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}
