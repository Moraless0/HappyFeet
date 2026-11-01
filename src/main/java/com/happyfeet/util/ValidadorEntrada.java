package com.happyfeet.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidadorEntrada {
    private static final Scanner scanner = new Scanner(System.in);

    public static int leerInt(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                LoggerUtil.mostrarError("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    public static int leerInt(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = scanner.nextInt();
                scanner.nextLine();
                if (valor >= min && valor <= max) {
                    return valor;
                }
                LoggerUtil.mostrarError("Error: El valor debe estar entre " + min + " y " + max + ".");
            } catch (InputMismatchException e) {
                scanner.nextLine();
                LoggerUtil.mostrarError("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    public static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                LoggerUtil.mostrarError("Error: Debe ingresar un número decimal válido.");
            }
        }
    }

    public static String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static String leerStringNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            LoggerUtil.mostrarError("Error: El campo no puede estar vacío.");
        }
    }

    public static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI")) {
                return true;
            } else if (respuesta.equals("N") || respuesta.equals("NO")) {
                return false;
            }
            LoggerUtil.mostrarError("Error: Debe ingresar S o N.");
        }
    }

    public static void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void limpiarBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    // recorta textos largos para no desalinear las tablas en consola
    public static String truncar(String texto, int max) {
        if (texto == null) {
            return "-";
        }
        String limpio = texto.trim();
        if (limpio.length() <= max) {
            return limpio;
        }
        if (max <= 3) {
            return limpio.substring(0, max);
        }
        return limpio.substring(0, max - 3) + "...";
    }

    // formatea valores nulos para mostrar en pantalla
    public static String texto(Object valor) {
        if (valor == null) {
            return "-";
        }
        String str = valor.toString().trim();
        return str.isEmpty() ? "-" : str;
    }
}
