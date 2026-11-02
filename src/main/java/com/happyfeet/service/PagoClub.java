package com.happyfeet.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagoClub implements EstrategiaPago {
    private final int puntosDisponibles;
    private static final BigDecimal VALOR_POR_PUNTO = new BigDecimal("100.00");

    public PagoClub(int puntosDisponibles) {
        this.puntosDisponibles = puntosDisponibles;
    }

    @Override
    public BigDecimal procesarPago(BigDecimal monto) {
        // calculamos cuantos puntos se pueden aplicar al monto
        int puntosAUsar = Math.min(
                puntosDisponibles,
                monto.divide(VALOR_POR_PUNTO, 0, RoundingMode.DOWN).intValue()
        );
        BigDecimal descuento = VALOR_POR_PUNTO.multiply(new BigDecimal(puntosAUsar));
        BigDecimal montoFinal = monto.subtract(descuento);
        return montoFinal.max(BigDecimal.ZERO);
    }

    @Override
    public String getDescripcion() {
        return "Pago con puntos del Club de Mascotas";
    }

    public int getPuntosUsados(BigDecimal monto) {
        return Math.min(
                puntosDisponibles,
                monto.divide(VALOR_POR_PUNTO, 0, RoundingMode.DOWN).intValue()
        );
    }
}
