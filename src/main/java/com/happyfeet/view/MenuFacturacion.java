package com.happyfeet.view;

import com.happyfeet.model.entities.Factura;
import com.happyfeet.model.enums.MetodoPago;
import com.happyfeet.service.FacturaService;
import com.happyfeet.util.ValidadorEntrada;

import java.math.BigDecimal;
import java.util.List;

public class MenuFacturacion {
    private FacturaService facturaService;

    public MenuFacturacion() {
        this.facturaService = new FacturaService();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("         MÓDULO DE FACTURACIÓN");
            System.out.println("========================================");
            System.out.println("  1. Generar Factura");
            System.out.println("  2. Listar Facturas de un Dueño");
            System.out.println("  3. Listar Todas las Facturas");
            System.out.println("  0. Volver al Menú Principal");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 3);

            switch (opcion) {
                case 1:
                    generarFactura();
                    break;
                case 2:
                    listarFacturasPorDueno();
                    break;
                case 3:
                    listarTodasFacturas();
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

    private void generarFactura() {
        System.out.println("\n----------------------------------------");
        System.out.println("            GENERAR FACTURA");
        System.out.println("----------------------------------------");
        int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");
        double subtotal = ValidadorEntrada.leerDouble("Subtotal ($): ");

        Factura factura = facturaService.crearFactura(duenoId, BigDecimal.valueOf(subtotal));
        System.out.println("\n--- MÉTODO DE PAGO ---");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        System.out.println("3. Transferencia");
        int metodoOpcion = ValidadorEntrada.leerInt("Seleccione método: ", 1, 3);

        MetodoPago metodo;
        switch (metodoOpcion) {
            case 1:
                metodo = MetodoPago.EFECTIVO;
                break;
            case 2:
                metodo = MetodoPago.TARJETA;
                break;
            case 3:
                metodo = MetodoPago.TRANSFERENCIA;
                break;
            default:
                metodo = MetodoPago.EFECTIVO;
        }

        int puntosClub = 0;
        if (metodo == MetodoPago.TARJETA) {
            puntosClub = ValidadorEntrada.leerInt("Puntos de club a canjear (0 si no aplica): ");
        }

        java.util.Map<Integer, Integer> insumosUsados = new java.util.HashMap<>();
        boolean registrarInsumos = ValidadorEntrada.leerBoolean("¿Desea registrar medicamentos o insumos utilizados?");
        while (registrarInsumos) {
            int productoId = ValidadorEntrada.leerInt("ID del producto/insumo: ");
            int cantidad = ValidadorEntrada.leerInt("Cantidad utilizada: ", 1, 9999);
            insumosUsados.put(productoId, cantidad);
            registrarInsumos = ValidadorEntrada.leerBoolean("¿Desea agregar otro medicamento/insumo?");
        }

        if (facturaService.procesarPago(factura, metodo, puntosClub, insumosUsados)) {
            System.out.println("\n✅ Pago procesado exitosamente.");
            if (!insumosUsados.isEmpty()) {
                System.out.println("📦 Stock de insumos y medicamentos descontado del inventario.");
            }
            System.out.printf("Total pagado: $%.2f%n", factura.getTotal());

            if (facturaService.generarFacturaArchivo(factura)) {
                System.out.println("✅ Archivo de comprobante generado en disco.");
            }
        } else {
            System.out.println("\n❌ No se pudo procesar el pago.");
        }
    }

    private void listarFacturasPorDueno() {
        System.out.println("\n----------------------------------------");
        System.out.println("         FACTURAS POR DUEÑO");
        System.out.println("----------------------------------------");
        int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");
        List<Factura> facturas = facturaService.listarFacturasPorDueno(duenoId);
        imprimirTablaFacturas(facturas);
    }

    private void listarTodasFacturas() {
        System.out.println("\n------------------------------------------------------------------------------------------------------");
        System.out.println("                                         TODAS LAS FACTURAS");
        List<Factura> facturas = facturaService.listarTodasFacturas();
        imprimirTablaFacturas(facturas);
    }

    private void imprimirTablaFacturas(List<Factura> facturas) {
        if (facturas.isEmpty()) {
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println("No hay facturas registradas.");
            System.out.println("------------------------------------------------------------------------------------------------------");
            return;
        }

        String separador = "+------+--------+----------------------+------------------+--------------+----------------+------------+";
        System.out.println(separador);
        System.out.printf("| %-4s | %-6s | %-20s | %-16s | %-12s | %-14s | %-10s |%n",
                "ID", "Dueño", "Nro. Factura", "Fecha Emisión", "Total", "Método Pago", "Estado");
        System.out.println(separador);
        for (Factura f : facturas) {
            String fecha = f.getFechaEmision() != null ? f.getFechaEmision().toString().replace("T", " ") : "-";
            if (fecha.length() > 16) {
                fecha = fecha.substring(0, 16);
            }
            String totalStr = f.getTotal() != null ? String.format("$%.2f", f.getTotal()) : "$0.00";
            System.out.printf("| %-4d | %-6d | %-20s | %-16s | %-12s | %-14s | %-10s |%n",
                    f.getId(),
                    f.getDuenoId(),
                    ValidadorEntrada.truncar(f.getNumeroFactura(), 20),
                    fecha,
                    totalStr,
                    f.getMetodoPago() != null ? f.getMetodoPago().name() : "-",
                    f.getEstado() != null ? f.getEstado().name() : "-");
        }
        System.out.println(separador);
        System.out.println("Total: " + facturas.size() + " factura(s).");
    }
}
