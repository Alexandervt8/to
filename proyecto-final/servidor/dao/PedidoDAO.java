package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Pedido;
import com.cafe.model.PedidoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public int crearPedidoConItems(Pedido p) throws SQLException {

        String sqlPed = "INSERT INTO pedidos(cliente_id, fecha, estado, metodo_pago, total) VALUES(?, ?, ?, ?, ?)";
        String sqlItem = "INSERT INTO pedido_items(pedido_id, producto_id, cantidad, precio_unitario, subtotal) VALUES(?,?,?,?,?)";

        try (Connection con = DB.getConnection()) {

            con.setAutoCommit(false); // iniciar transacción

            try {

                // Insertar pedido principal
                try (PreparedStatement ps = con.prepareStatement(sqlPed, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, p.getClienteId());

                    // ← AQUÍ ESTA LA CORRECCIÓN: fecha ya es String
                    ps.setString(2, p.getFecha());

                    ps.setString(3, p.getEstado().name());
                    ps.setString(4, p.getMetodoPago().name());
                    ps.setDouble(5, p.getTotal());

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) p.setId(rs.getInt(1));
                    }
                }

                // Insertar ítems
                try (PreparedStatement ps = con.prepareStatement(sqlItem)) {
                    for (PedidoItem it : p.getItems()) {

                        ps.setInt(1, p.getId());
                        ps.setInt(2, it.getProductoId());
                        ps.setInt(3, it.getCantidad());
                        ps.setDouble(4, it.getPrecioUnitario());
                        ps.setDouble(5, it.getSubtotal());

                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                con.commit();
                return p.getId();

            } catch (SQLException ex) {

                con.rollback();
                throw ex;

            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public List<String> listarPedidosCortos() throws SQLException {

        String sql = "SELECT p.id, c.nombre, p.fecha, p.estado, p.total " +
                     "FROM pedidos p JOIN clientes c ON p.cliente_id=c.id " +
                     "ORDER BY p.fecha DESC LIMIT 50";

        List<String> out = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(String.format("[%d] %s | %s | %s | S/ %.2f",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5)
                ));
            }
        }
        return out;
    }
}
