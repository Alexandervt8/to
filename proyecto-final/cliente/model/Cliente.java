package com.cafe.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
    private boolean activo;
    private String fechaRegistro; 
    public Cliente() {}

    public Cliente(int id, String nombre, String email, String telefono,
                   boolean activo, String fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    // ===============================
    // GETTERS & SETTERS
    // ===============================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) {this.fechaRegistro = fechaRegistro;    }
}
