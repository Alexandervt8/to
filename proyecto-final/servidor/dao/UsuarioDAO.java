package com.cafe.dao;

import com.cafe.db.DB;
import com.cafe.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public int crear(Usuario u) throws Exception {

        String sql = """
            INSERT INTO usuarios (nombre, usuario, password, rol, activo)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRol());
            ps.setBoolean(5, u.isActivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }
}
