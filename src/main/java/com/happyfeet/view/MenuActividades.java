package com.happyfeet.view;

import com.happyfeet.service.Comprobante;
import com.happyfeet.service.GeneradorComprobante;
import com.happyfeet.util.ValidadorEntrada;

public class MenuActividades {
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("         ACTIVIDADES ESPECIALES");
            System.out.println("========================================");
            System.out.println("  1. Generar Contrato de Adopción");
            System.out.println("  2. Generar Receta Médica");
            System.out.println("  0. Volver al Menú Principal");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 2);

            switch (opcion) {
                case 1:
                    generarContratoAdopcion();
                    break;
                case 2:
                    generarReceta();
                    break;
                case 0:
                    System.out.println("\nVolviendo al menú principal...");
                    break;
            }

            if (opcion != 0) {
                ValidadorEntrada.pausar();
            }
        } while (opcion != 0);
    }

    private void generarContratoAdopcion() {
        System.out.println("\n----------------------------------------");
        System.out.println("       GENERAR CONTRATO DE ADOPCIÓN");
        System.out.println("----------------------------------------");
        String nombreArchivo = "contrato_adopcion_" + System.currentTimeMillis() + ".txt";

        try {
            Comprobante comprobante = GeneradorComprobante.crearComprobante("adopcion", null);
            comprobante.generar(nombreArchivo);
            System.out.println("\n✅ Contrato de adopción generado exitosamente: " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("\n❌ Error al generar contrato: " + e.getMessage());
        }
    }

    private void generarReceta() {
        System.out.println("\n----------------------------------------");
        System.out.println("         GENERAR RECETA MÉDICA");
        System.out.println("----------------------------------------");
        String nombreArchivo = "receta_" + System.currentTimeMillis() + ".txt";

        try {
            Comprobante comprobante = GeneradorComprobante.crearComprobante("receta", null);
            comprobante.generar(nombreArchivo);
            System.out.println("\n✅ Receta médica generada exitosamente: " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("\n❌ Error al generar receta: " + e.getMessage());
        }
    }
}
