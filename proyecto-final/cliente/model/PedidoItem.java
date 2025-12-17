package com.cafe.model;

import java.io.Serializable;

public class PedidoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int pedidoId;
    private int productoId;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private String estado; // pendiente, preparando, listo, servido

    public PedidoItem() {}

    public PedidoItem(int id, int pedidoId, int productoId, int cantidad,
                      double precioUnitario, double subtotal, String estado) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.estado = estado;
    }

    // ===============================
    // GETTERS & SETTERS
    // ===============================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
