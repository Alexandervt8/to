package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDAO {

    // ===============================
    // SQL
    // ===============================
    private static final String INSERT_SQL =
        "INSERT INTO categorias(nombre) VALUES (?)";

    private static final String SELECT_ALL_SQL =
        "SELECT id, nombre FROM categorias ORDER BY nombre";

    private static final String SELECT_BY_ID_SQL =
        "SELECT id, nombre FROM categorias WHERE id = ?";

    // ===============================
    // CREAR CATEGORIA
    // ===============================
    public int crear(Categoria c) throws SQLException {

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
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
    // LISTAR CATEGORÍAS
    // ===============================
    public List<Categoria> listar() throws SQLException {

        List<Categoria> out = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                out.add(c);
            }
        }
        return out;
    }

    // ===============================
    // BUSCAR POR ID (opcional pero útil)
    // ===============================
    public Optional<Categoria> buscarPorId(int id) throws SQLException {

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNombre(rs.getString("nombre"));
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }
}
