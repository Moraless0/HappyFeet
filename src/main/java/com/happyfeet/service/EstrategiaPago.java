package com.happyfeet.service;

import java.math.BigDecimal;

public interface EstrategiaPago {
    BigDecimal procesarPago(BigDecimal monto);
    String getDescripcion();
}
