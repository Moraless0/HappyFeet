package com.happyfeet.service;

import com.happyfeet.util.LoggerUtil;
import java.io.IOException;

public abstract class Comprobante {
    protected String contenido;

    public Comprobante() {
        this.contenido = "";
    }

    public abstract void generar(String nombreArchivo) throws IOException;

    protected void guardarEnArchivo(String nombreArchivo) throws IOException {
        try (java.io.FileWriter writer = new java.io.FileWriter(nombreArchivo)) {
            writer.write(contenido);
        } catch (IOException e) {
            LoggerUtil.registrarError("Error al generar comprobante: " + nombreArchivo, e);
            throw e;
        }
    }
}
