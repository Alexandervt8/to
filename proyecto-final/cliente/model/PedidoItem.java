package com.cafe.model;

import java.io.Serializable;

public class PedidoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int productoId;
    private int cantidad;
    private double precioUnitario;

    public PedidoItem() {}

    public PedidoItem(int id, int productoId, int cantidad, double precioUnitario) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
}

