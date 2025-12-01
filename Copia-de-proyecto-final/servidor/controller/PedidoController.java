package com.cafe.controller;

import com.cafe.dao.PedidoDAO;
import com.cafe.model.Pedido;

public class PedidoController {

    public boolean registrarPedido(Pedido pedido) {
        try {
            PedidoDAO dao = new PedidoDAO();
            dao.crearPedidoConItems(pedido); // ← método correcto
            return true;

        } catch (Exception e) {
            System.out.println("Error controlador: " + e.getMessage());
            return false;
        }
    }
}
