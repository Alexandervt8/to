package com.cafe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Estado {
        PENDIENTE,
        PAGADO,
        CANCELADO
    }

    public enum MetodoPago {
        EFECTIVO,
        TARJETA,
        YAPE,
        PLIN
    }

    private int id;
    private int clienteId;
    private String fecha;
    private double total;
    private Estado estado;
    private MetodoPago metodoPago;

    private List<PedidoItem> items = new ArrayList<>();

    public Pedido() {}

    public Pedido(int id, int clienteId, String fecha, double total, Estado estado, MetodoPago metodoPago) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public List<PedidoItem> getItems() { return items; }
    public void addItem(PedidoItem item) { items.add(item); }
}
