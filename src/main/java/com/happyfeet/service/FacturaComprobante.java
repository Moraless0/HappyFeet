package com.happyfeet.service;

import com.happyfeet.model.entities.Factura;
import java.io.IOException;

public class FacturaComprobante extends Comprobante {
    private Factura factura;

    public FacturaComprobante(Factura factura) {
        this.factura = factura;
    }

    @Override
    public void generar(String nombreArchivo) throws IOException {
        contenido = "========================================\n";
        contenido += "       VETERINARIA HAPPY FEET\n";
        contenido += "========================================\n";
        contenido += "FACTURA: " + factura.getNumeroFactura() + "\n";
        contenido += "FECHA: " + factura.getFechaEmision() + "\n";
        contenido += "----------------------------------------\n";
        contenido += "SUBTOTAL: $" + factura.getSubtotal() + "\n";
        contenido += "IMPUESTO: $" + factura.getImpuesto() + "\n";
        contenido += "DESCUENTO: $" + factura.getDescuento() + "\n";
        contenido += "TOTAL: $" + factura.getTotal() + "\n";
        contenido += "MÉTODO DE PAGO: " + factura.getMetodoPago() + "\n";
        contenido += "ESTADO: " + factura.getEstado() + "\n";
        contenido += "========================================\n";
        contenido += "¡Gracias por su visita!\n";
        contenido += "========================================\n";

        guardarEnArchivo(nombreArchivo);
    }
}
