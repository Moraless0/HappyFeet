package com.happyfeet.view;

import com.happyfeet.util.ValidadorEntrada;

public class MenuPrincipal {
    private MenuPacientes menuPacientes;
    private MenuInventario menuInventario;
    private MenuCitas menuCitas;
    private MenuFacturacion menuFacturacion;
    private MenuActividades menuActividades;

    public MenuPrincipal() {
        this.menuPacientes = new MenuPacientes();
        this.menuInventario = new MenuInventario();
        this.menuCitas = new MenuCitas();
        this.menuFacturacion = new MenuFacturacion();
        this.menuActividades = new MenuActividades();
    }

    public void iniciar() {
        System.out.println("\n========================================");
        System.out.println("         VETERINARIA HAPPY FEET");
        System.out.println("      Sistema de Gestión Integral");
        System.out.println("========================================");

        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("             MENÚ PRINCIPAL");
            System.out.println("========================================");
            System.out.println("  1. Pacientes (Dueños y Mascotas)");
            System.out.println("  2. Inventario");
            System.out.println("  3. Citas");
            System.out.println("  4. Facturación");
            System.out.println("  5. Actividades Especiales");
            System.out.println("  0. Salir");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 5);

            switch (opcion) {
                case 1:
                    menuPacientes.mostrarMenu();
                    break;
                case 2:
                    menuInventario.mostrarMenu();
                    break;
                case 3:
                    menuCitas.mostrarMenu();
                    break;
                case 4:
                    menuFacturacion.mostrarMenu();
                    break;
                case 5:
                    menuActividades.mostrarMenu();
                    break;
                case 0:
                    System.out.println("\n========================================");
                    System.out.println(" ¡Gracias por usar el sistema Happy Feet!");
                    System.out.println("========================================\n");
                    break;
            }
        } while (opcion != 0);
    }
}
