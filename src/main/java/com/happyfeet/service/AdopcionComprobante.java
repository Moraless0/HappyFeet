package com.happyfeet.service;

import java.io.IOException;

public class AdopcionComprobante extends Comprobante {
    @Override
    public void generar(String nombreArchivo) throws IOException {
        contenido = "========================================\n";
        contenido += "       VETERINARIA HAPPY FEET\n";
        contenido += "       CONTRATO DE ADOPCIÓN\n";
        contenido += "========================================\n";
        contenido += "FECHA: " + java.time.LocalDateTime.now() + "\n";
        contenido += "----------------------------------------\n";
        contenido += "DATOS DEL ADOPTANTE:\n";
        contenido += "Nombre: [Nombre del adoptante]\n";
        contenido += "Documento: [Documento de identidad]\n";
        contenido += "----------------------------------------\n";
        contenido += "DATOS DE LA MASCOTA:\n";
        contenido += "Nombre: [Nombre de la mascota]\n";
        contenido += "Especie: [Especie]\n";
        contenido += "Raza: [Raza]\n";
        contenido += "----------------------------------------\n";
        contenido += "CONDICIONES:\n";
        contenido += "- El adoptante se compromete a cuidar\n";
        contenido += "  de la mascota adecuadamente.\n";
        contenido += "- Se realizará seguimiento periódico.\n";
        contenido += "========================================\n";
        contenido += "Firma Adoptante: _________________\n";
        contenido += "Firma Veterinaria: _________________\n";
        contenido += "========================================\n";

        guardarEnArchivo(nombreArchivo);
    }
}
