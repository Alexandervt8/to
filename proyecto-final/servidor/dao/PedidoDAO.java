package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Pedido;
import com.cafe.model.PedidoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // ===============================
    // CREAR PEDIDO CON ITEMS
    // ===============================
    public int crearPedidoConItems(Pedido p) throws SQLException {

        String sqlPedido = """
            INSERT INTO pedidos
            (cliente_id, mesa_id, usuario_id, tipo, estado)
            VALUES (?, ?, ?, ?, ?)
        """;

        String sqlItem = """
            INSERT INTO pedido_items
            (pedido_id, producto_id, cantidad, precio_unitario, subtotal, estado)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        String sqlPrecioProducto =
            "SELECT precio FROM productos WHERE id = ? AND activo = 1";

        Connection con = DB.getConnection();
        DB.begin(con);

        try {
            // ===============================
            // INSERTAR PEDIDO
            // ===============================
            try (PreparedStatement ps = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, p.getClienteId());

                if (p.getMesaId() != null) {
                    ps.setInt(2, p.getMesaId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }

                if (p.getUsuarioId() != null) {
                    ps.setInt(3, p.getUsuarioId());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setString(4, p.getTipo() != null ? p.getTipo() : "mesa");
                ps.setString(5, p.getEstado() != null ? p.getEstado() : "abierto");

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                    } else {
                        throw new SQLException("No se pudo generar el ID del pedido.");
                    }
                }
            }

            // ===============================
            // INSERTAR ITEMS
            // ===============================
            try (PreparedStatement psItem = con.prepareStatement(sqlItem);
                 PreparedStatement psPrecio = con.prepareStatement(sqlPrecioProducto)) {

                for (PedidoItem it : p.getItems()) {

                    // Obtener precio REAL del producto
                    psPrecio.setInt(1, it.getProductoId());
                    try (ResultSet rs = psPrecio.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Producto no válido o inactivo: " + it.getProductoId());
                        }

                        double precioUnitario = rs.getDouble("precio");
                        double subtotal = precioUnitario * it.getCantidad();

                        psItem.setInt(1, p.getId());
                        psItem.setInt(2, it.getProductoId());
                        psItem.setInt(3, it.getCantidad());
                        psItem.setDouble(4, precioUnitario);
                        psItem.setDouble(5, subtotal);
                        psItem.setString(6, it.getEstado() != null ? it.getEstado() : "pendiente");

                        psItem.addBatch();
                    }
                }
                psItem.executeBatch();
            }

            // ===============================
            // COMMIT
            // ===============================
            DB.commit(con);
            return p.getId();

        } catch (SQLException ex) {
            DB.rollback(con);
            throw ex;
        } finally {
            DB.close(con);
        }
    }

    // ===============================
    // LISTADO SIMPLE DE PEDIDOS
    // ===============================
    public List<String> listarPedidosCortos() throws SQLException {

        String sql = """
            SELECT p.id, c.nombre, p.fecha, p.estado
            FROM pedidos p
            JOIN clientes c ON p.cliente_id = c.id
            ORDER BY p.fecha DESC
            LIMIT 50
        """;

        List<String> out = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(String.format(
                    "[%d] %s | %s | %s",
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("fecha"),
                    rs.getString("estado")
                ));
            }
        }
        return out;
    }
}

