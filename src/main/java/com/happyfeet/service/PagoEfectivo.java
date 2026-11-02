package com.happyfeet.service;

import java.math.BigDecimal;

public class PagoEfectivo implements EstrategiaPago {
    @Override
    public BigDecimal procesarPago(BigDecimal monto) {
        return monto;
    }

    @Override
    public String getDescripcion() {
        return "Pago en efectivo";
    }
}
