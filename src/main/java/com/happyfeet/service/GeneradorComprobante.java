package com.happyfeet.service;

import com.happyfeet.model.entities.Factura;

public class GeneradorComprobante {
    public static Comprobante crearComprobante(String tipo, Object datos) {
        switch (tipo.toLowerCase()) {
            case "factura":
                if (datos instanceof Factura) {
                    return new FacturaComprobante((Factura) datos);
                }
                break;
            case "receta":
                return new RecetaComprobante();
            case "adopcion":
                return new AdopcionComprobante();
            default:
                throw new IllegalArgumentException("Tipo de comprobante no válido: " + tipo);
        }
        throw new IllegalArgumentException("Datos no válidos para el tipo: " + tipo);
    }
}
