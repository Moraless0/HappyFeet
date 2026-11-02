package com.happyfeet.service;

import java.math.BigDecimal;

public class PagoTarjeta implements EstrategiaPago {
    @Override
    public BigDecimal procesarPago(BigDecimal monto) {
        return monto;
    }

    @Override
    public String getDescripcion() {
        return "Pago con tarjeta de crédito/débito";
    }
}
