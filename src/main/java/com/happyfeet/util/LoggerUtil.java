package com.happyfeet.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    private static final String LOG_FILE = "errores.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // alias para no romper llamadas viejas
    public static void logError(String mensajeUsuario, Exception excepcion) {
        registrarError(mensajeUsuario, excepcion);
    }

    public static void registrarError(String mensajeUsuario, Exception excepcion) {
        String timestamp = LocalDateTime.now().format(formatter);
        String mensajeCompleto = String.format("[%s] %s\nError técnico: %s\n",
                timestamp, mensajeUsuario, excepcion != null ? excepcion.toString() : "N/A");

        if (excepcion != null) {
            for (StackTraceElement element : excepcion.getStackTrace()) {
                mensajeCompleto += "\t" + element.toString() + "\n";
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(mensajeCompleto);
            writer.println("--------------------------------------------------");
        } catch (IOException e) {
            System.out.println("Error al escribir en log: " + e.getMessage());
        }
    }

    public static void mostrarError(String mensajeUsuario) {
        System.out.println("\n⚠️ " + mensajeUsuario);
        System.out.println("Por favor, intente nuevamente.\n");
    }
}
