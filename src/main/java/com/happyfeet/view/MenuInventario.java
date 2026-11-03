package com.happyfeet.view;

import com.happyfeet.model.entities.Producto;
import com.happyfeet.service.InventarioService;
import com.happyfeet.util.ValidadorEntrada;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MenuInventario {
    private InventarioService inventarioService;

    public MenuInventario() {
        this.inventarioService = new InventarioService();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("          MÓDULO DE INVENTARIO");
            System.out.println("========================================");
            System.out.println("  1. Listar Productos");
            System.out.println("  2. Registrar Producto");
            System.out.println("  3. Agregar Stock");
            System.out.println("  4. Reporte Stock Bajo");
            System.out.println("  5. Reporte Próximos a Vencer");
            System.out.println("  6. Valor Total del Inventario");
            System.out.println("  0. Volver al Menú Principal");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 6);

            switch (opcion) {
                case 1:
                    listarProductos();
                    break;
                case 2:
                    registrarProducto();
                    break;
                case 3:
                    agregarStock();
                    break;
                case 4:
                    reporteStockBajo();
                    break;
                case 5:
                    reporteProximosAVencer();
                    break;
                case 6:
                    reporteValorInventario();
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

    private void listarProductos() {
        System.out.println("\n------------------------------------------------------------------------------------------------");
        System.out.println("                                      LISTA DE PRODUCTOS");
        List<Producto> productos = inventarioService.listarProductos();
        imprimirTablaProductos(productos);
    }

    private void registrarProducto() {
        System.out.println("\n----------------------------------------");
        System.out.println("        REGISTRAR NUEVO PRODUCTO");
        System.out.println("----------------------------------------");
        String nombre = ValidadorEntrada.leerStringNoVacio("Nombre del producto: ");
        int tipoId = ValidadorEntrada.leerInt("Tipo (1=Med, 2=Vacuna, 3=Insumo, 4=Alimento, 5=Accesorio): ", 1, 5);
        double precio = ValidadorEntrada.leerDouble("Precio de venta ($): ");
        int stock = ValidadorEntrada.leerInt("Cantidad inicial: ");
        int stockMinimo = ValidadorEntrada.leerInt("Stock mínimo: ");
        System.out.print("Fecha de vencimiento (YYYY-MM-DD) o Enter para omitir: ");
        String fechaStr = ValidadorEntrada.leerString("");
        boolean requiereReceta = ValidadorEntrada.leerBoolean("¿Requiere receta médica?");

        Producto producto = new Producto(nombre, tipoId, BigDecimal.valueOf(precio), stock);
        producto.setStockMinimo(stockMinimo);
        producto.setRequiereReceta(requiereReceta);

        if (!fechaStr.isEmpty()) {
            try {
                producto.setFechaVencimiento(LocalDate.parse(fechaStr.trim()));
            } catch (Exception e) {
                System.out.println("⚠️ Fecha inválida, se guardará sin vencimiento.");
            }
        }

        if (inventarioService.registrarProducto(producto)) {
            System.out.println("\n✅ Producto registrado exitosamente.");
        } else {
            System.out.println("\n❌ No se pudo registrar el producto.");
        }
    }

    private void agregarStock() {
        System.out.println("\n----------------------------------------");
        System.out.println("              AGREGAR STOCK");
        System.out.println("----------------------------------------");
        int productoId = ValidadorEntrada.leerInt("ID del producto: ");
        int cantidad = ValidadorEntrada.leerInt("Cantidad a ingresar: ");

        if (inventarioService.actualizarStock(productoId, cantidad)) {
            System.out.println("\n✅ Stock actualizado exitosamente.");
        } else {
            System.out.println("\n❌ No se pudo actualizar el stock.");
        }
    }

    private void reporteStockBajo() {
        System.out.println("\n------------------------------------------------------------------------------------------------");
        System.out.println("                         REPORTE: PRODUCTOS CON STOCK BAJO (REABASTECER)");
        List<Producto> productos = inventarioService.reporteStockBajo();
        if (productos.isEmpty()) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("✅ Todo el inventario se encuentra por encima del stock mínimo.");
            System.out.println("------------------------------------------------------------------------------------------------");
            return;
        }
        imprimirTablaProductos(productos);
    }

    private void reporteProximosAVencer() {
        System.out.println("\n----------------------------------------");
        System.out.println("        PRÓXIMOS A VENCER");
        System.out.println("----------------------------------------");
        int dias = ValidadorEntrada.leerInt("Días de anticipación a consultar: ");

        System.out.println("\n------------------------------------------------------------------------------------------------");
        System.out.println("                   REPORTE: PRODUCTOS PRÓXIMOS A VENCER (" + dias + " DÍAS)");
        List<Producto> productos = inventarioService.reporteProximosAVencer(dias);
        if (productos.isEmpty()) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("✅ No hay productos próximos a vencer en el periodo indicado.");
            System.out.println("------------------------------------------------------------------------------------------------");
            return;
        }
        imprimirTablaProductos(productos);
    }

    private void reporteValorInventario() {
        System.out.println("\n========================================");
        System.out.println("       VALOR TOTAL DEL INVENTARIO");
        System.out.println("========================================");
        BigDecimal total = inventarioService.calcularValorTotalInventario();
        System.out.printf("  Total estimado en stock: $%.2f%n", total);
        System.out.println("========================================");
    }

    private void imprimirTablaProductos(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("No hay productos registrados.");
            System.out.println("------------------------------------------------------------------------------------------------");
            return;
        }

        String separador = "+------+--------------------------------+---------+---------+------------+--------------+";
        System.out.println(separador);
        System.out.printf("| %-4s | %-30s | %-7s | %-7s | %-10s | %-12s |%n",
                "ID", "Nombre", "Stock", "Mínimo", "Precio", "Vencimiento");
        System.out.println(separador);
        for (Producto p : productos) {
            String precioStr = p.getPrecioVenta() != null ? String.format("$%.2f", p.getPrecioVenta()) : "$0.00";
            String venc = p.getFechaVencimiento() != null ? p.getFechaVencimiento().toString() : "-";
            System.out.printf("| %-4d | %-30s | %-7d | %-7d | %-10s | %-12s |%n",
                    p.getId(),
                    ValidadorEntrada.truncar(p.getNombreProducto(), 30),
                    p.getCantidadStock(),
                    p.getStockMinimo(),
                    precioStr,
                    venc);
        }
        System.out.println(separador);
        System.out.println("Total: " + productos.size() + " producto(s).");
    }
}
