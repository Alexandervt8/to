package com.cafe.dao;


import com.cafe.db.DB;
import com.cafe.model.Categoria;
import java.sql.*;
import java.util.*;


public class CategoriaDAO {
public int crear(Categoria c) throws SQLException {
String sql = "INSERT INTO categorias(nombre) VALUES(?)";
try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, c.getNombre());
ps.executeUpdate();
try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
}
return 0;
}


public List<Categoria> listar() throws SQLException {
List<Categoria> out = new ArrayList<>();
try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT id,nombre FROM categorias ORDER BY nombre"); ResultSet rs = ps.executeQuery()) {
while (rs.next()) out.add(new Categoria(rs.getInt(1), rs.getString(2)));
}
return out;
}
}