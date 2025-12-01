package com.cafe.dao;


import com.cafe.db.DB;
import com.cafe.model.Producto;
import java.sql.*;
import java.util.*;


public class ProductoDAO {
public int crear(Producto p) throws SQLException {
String sql = "INSERT INTO productos(nombre,precio,categoria_id,activo) VALUES(?,?,?,?)";
try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, p.getNombre());
ps.setDouble(2, p.getPrecio());
ps.setInt(3, p.getCategoriaId());
ps.setBoolean(4, p.isActivo());
ps.executeUpdate();
try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
}
return 0;
}


public List<Producto> listarActivos() throws SQLException {
List<Producto> out = new ArrayList<>();
String sql = "SELECT id,nombre,precio,categoria_id,activo FROM productos WHERE activo=1 ORDER BY nombre";
try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
out.add(new Producto(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getBoolean(5)));
}
}
return out;
}


public Producto buscarPorId(int id) throws SQLException {
String sql = "SELECT id,nombre,precio,categoria_id,activo FROM productos WHERE id=?";
try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
ps.setInt(1, id);
try (ResultSet rs = ps.executeQuery()) {
if (rs.next()) {
return new Producto(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getBoolean(5));
}
}
}
return null;
}
}