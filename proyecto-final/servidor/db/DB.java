package com.cafe.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String URL =
        "jdbc:mysql://localhost:3306/cafe_db?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando driver MySQL", e);
        }
    }

    /**
     * Conexión NORMAL (autoCommit = true)
     * Para CRUD simples
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Inicia una transacción manual
     */
    public static void begin(Connection conn) throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(false);
        }
    }

    /**
     * Commit seguro
     */
    public static void commit(Connection conn) {
        try {
            if (conn != null) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback seguro
     */
    public static void rollback(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierre seguro
     */
    public static void close(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
