package com.happyfeet.service;

import java.io.IOException;

public class RecetaComprobante extends Comprobante {
    @Override
    public void generar(String nombreArchivo) throws IOException {
        contenido = "========================================\n";
        contenido += "       VETERINARIA HAPPY FEET\n";
        contenido += "           RECETA MÉDICA\n";
        contenido += "========================================\n";
        contenido += "FECHA: " + java.time.LocalDateTime.now() + "\n";
        contenido += "----------------------------------------\n";
        contenido += "MEDICAMENTO: [Nombre del medicamento]\n";
        contenido += "DOSIS: [Dosis indicada]\n";
        contenido += "FRECUENCIA: [Frecuencia de administración]\n";
        contenido += "DURACIÓN: [Días de tratamiento]\n";
        contenido += "INSTRUCCIONES: [Instrucciones especiales]\n";
        contenido += "========================================\n";
        contenido += "Firma del Veterinario: _________________\n";
        contenido += "========================================\n";

        guardarEnArchivo(nombreArchivo);
    }
}
