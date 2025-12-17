package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // ===============================
    // CREAR PRODUCTO
    // ===============================
    public int crear(Producto p) throws SQLException {

        String sql = """
            INSERT INTO productos
            (nombre, precio, tiempo_preparacion, categoria_id, activo)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getTiempoPreparacion());
            ps.setInt(4, p.getCategoriaId());
            ps.setBoolean(5, p.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // ===============================
    // LISTAR PRODUCTOS ACTIVOS
    // ===============================
    public List<Producto> listarActivos() throws SQLException {

        List<Producto> out = new ArrayList<>();

        String sql = """
            SELECT id, nombre, precio, tiempo_preparacion, categoria_id, activo
            FROM productos
            WHERE activo = 1
            ORDER BY nombre
        """;

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                p.setCategoriaId(rs.getInt("categoria_id"));
                p.setActivo(rs.getBoolean("activo"));

                out.add(p);
            }
        }
        return out;
    }

    // ===============================
    // BUSCAR PRODUCTO POR ID
    // ===============================
    public Producto buscarPorId(int id) throws SQLException {

        String sql = """
            SELECT id, nombre, precio, tiempo_preparacion, categoria_id, activo
            FROM productos
            WHERE id = ?
        """;

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                    p.setCategoriaId(rs.getInt("categoria_id"));
                    p.setActivo(rs.getBoolean("activo"));
                    return p;
                }
            }
        }
        return null;
    }

    // ===============================
    // DESACTIVAR PRODUCTO (NO DELETE)
    // ===============================
    public boolean desactivar(int id) throws SQLException {

        String sql = "UPDATE productos SET activo = 0 WHERE id = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
