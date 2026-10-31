package com.happyfeet.model.entities;

import com.happyfeet.model.enums.EstadoFactura;
import com.happyfeet.model.enums.MetodoPago;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Factura {
    private int id;
    private int duenoId;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private EstadoFactura estado;
    private String observaciones;

    public Factura() {
    }

    public Factura(int duenoId, String numeroFactura, BigDecimal subtotal, BigDecimal total) {
        this.duenoId = duenoId;
        this.numeroFactura = numeroFactura;
        this.subtotal = subtotal;
        this.impuesto = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.total = total;
        this.fechaEmision = LocalDateTime.now();
        this.estado = EstadoFactura.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuenoId() {
        return duenoId;
    }

    public void setDuenoId(int duenoId) {
        this.duenoId = duenoId;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", numero='" + numeroFactura + '\'' +
                ", total=" + total +
                ", estado=" + estado +
                '}';
    }
}
