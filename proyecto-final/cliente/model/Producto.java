package com.cafe.model;

import java.io.Serializable;

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private double precio;
    private int tiempoPreparacion; // minutos
    private int categoriaId;
    private boolean activo;

    public Producto() {}

    public Producto(int id, String nombre, double precio, int tiempoPreparacion,
                    int categoriaId, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.categoriaId = categoriaId;
        this.activo = activo;
    }

    // ===============================
    // GETTERS & SETTERS
    // ===============================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
