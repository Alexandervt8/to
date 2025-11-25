package com.cafe.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    // 👇 usa cafe_db (tu nombre real de BD)
    private static final String URL  = "jdbc:mysql://localhost:3306/cafe_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP por defecto

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el Driver de MySQL. Agrega mysql-connector-j al classpath.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
