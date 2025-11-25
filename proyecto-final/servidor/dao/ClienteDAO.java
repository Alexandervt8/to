package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO {

    // SQL
    private static final String INSERT_SQL =
            "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE clientes SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM clientes WHERE id = ?";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, nombre, email, telefono FROM clientes WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, nombre, email, telefono FROM clientes ORDER BY id DESC";
    private static final String SELECT_BY_EMAIL_SQL =
            "SELECT id, nombre, email, telefono FROM clientes WHERE email = ?";

    /** Crea un cliente y devuelve el ID generado. */
    public int create(Cliente c) throws SQLException {
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("No se pudo insertar el cliente, 0 filas afectadas.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    c.setId(id);
                    return id;
                } else {
                    throw new SQLException("No se obtuvo ID generado para el cliente.");
                }
            }
        }
    }

    /** Actualiza un cliente existente por ID (>0). */
    public boolean update(Cliente c) throws SQLException {
      
        if (c.getId() <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser > 0 para actualizar.");
        }
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            ps.setInt(4, c.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina fisicamente un cliente por ID. */
    public boolean delete(int id) throws SQLException {
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Busca un cliente por ID. */
    public Optional<Cliente> findById(int id) throws SQLException {
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    /** Lista todos los clientes. */
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

    /** Busca por email. */
    public Optional<Cliente> findByEmail(String email) throws SQLException {
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    private Cliente mapRow(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setEmail(rs.getString("email"));
        c.setTelefono(rs.getString("telefono"));
 
        return c;
    }

    public int crear(Cliente c) throws SQLException { return create(c); }
    public List<Cliente> listar() throws SQLException { return findAll(); }
    public boolean desactivar(int id) throws SQLException { return delete(id); }
}
