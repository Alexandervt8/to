package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    // ===============================
    // SQL
    // ===============================
    private static final String INSERT_SQL =
        "INSERT INTO clientes(nombre, documento, email, telefono, activo) VALUES (?, ?, ?, ?, 1)";

    private static final String UPDATE_SQL =
        "UPDATE clientes SET nombre = ?, documento = ?, email = ?, telefono = ? WHERE id = ?";

    private static final String DESACTIVATE_SQL =
        "UPDATE clientes SET activo = 0 WHERE id = ?";

    private static final String SELECT_BY_ID_SQL =
        "SELECT id, nombre, documento, email, telefono, activo FROM clientes WHERE id = ?";

    private static final String SELECT_ALL_SQL =
        "SELECT id, nombre, documento, email, telefono, activo FROM clientes WHERE activo = 1 ORDER BY id DESC";

    private static final String SELECT_BY_EMAIL_SQL =
        "SELECT id, nombre, documento, email, telefono, activo FROM clientes WHERE email = ? AND activo = 1";

    // ===============================
    // CREAR CLIENTE
    // ===============================
    public int create(Cliente c) throws SQLException {

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelefono());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("No se pudo insertar el cliente.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    c.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("No se obtuvo ID generado.");
    }

    // ===============================
    // ACTUALIZAR CLIENTE
    // ===============================
    public boolean update(Cliente c) throws SQLException {

        if (c.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido para actualizar.");
        }

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelefono());
            ps.setInt(5, c.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // ===============================
    // DESACTIVAR CLIENTE (SOFT DELETE)
    // ===============================
    public boolean desactivar(int id) throws SQLException {

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(DESACTIVATE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ===============================
    // BUSCAR POR ID
    // ===============================
    public Optional<Cliente> findById(int id) throws SQLException {

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // ===============================
    // LISTAR ACTIVOS
    // ===============================
    public List<Cliente> findAll() throws SQLException {

        List<Cliente> lista = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    // ===============================
    // BUSCAR POR EMAIL
    // ===============================
    public Optional<Cliente> findByEmail(String email) throws SQLException {

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    // ===============================
    // MAPEO
    // ===============================
    private Cliente mapRow(ResultSet rs) throws SQLException {

        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setDocumento(rs.getString("documento"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getString("telefono"));
        c.setActivo(rs.getBoolean("activo"));

        return c;
    }

    // ===============================
    // MÉTODOS LEGACY (compatibilidad)
    // ===============================
    public int crear(Cliente c) throws SQLException { return create(c); }
    public List<Cliente> listar() throws SQLException { return findAll(); }
}
