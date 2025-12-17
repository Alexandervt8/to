package com.cafe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int clienteId;
    private Integer mesaId;     // nullable
    private Integer usuarioId;  // nullable
    private String tipo;        // mesa, delivery, llevar
    private String estado;      // abierto, cerrado, cancelado
    private String fecha;       
    private double total;

    private List<PedidoItem> items = new ArrayList<>();

    public Pedido() {}

    // ===============================
    // GETTERS & SETTERS
    // ===============================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public Integer getMesaId() { return mesaId; }
    public void setMesaId(Integer mesaId) { this.mesaId = mesaId; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<PedidoItem> getItems() { return items; }
    public void setItems(List<PedidoItem> items) { this.items = items; }

    public void addItem(PedidoItem item) {
        this.items.add(item);
    }
}

