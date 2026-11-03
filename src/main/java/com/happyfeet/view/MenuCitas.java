package com.happyfeet.view;

import com.happyfeet.model.entities.Cita;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.service.CitaService;
import com.happyfeet.service.MascotaService;
import com.happyfeet.util.LoggerUtil;
import com.happyfeet.util.ValidadorEntrada;

import java.time.LocalDateTime;
import java.util.List;

public class MenuCitas {

    private final CitaService citaService;
    private final MascotaService mascotaService;

    public MenuCitas() {
        this.citaService = new CitaService();
        this.mascotaService = new MascotaService();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("            MÓDULO DE CITAS");
            System.out.println("========================================");
            System.out.println("  1. Programar Cita");
            System.out.println("  2. Listar Citas de una Mascota");
            System.out.println("  3. Listar Todas las Citas");
            System.out.println("  4. Citas de Hoy");
            System.out.println("  5. Buscar Próxima Cita Pendiente");
            System.out.println("  0. Volver al Menú Principal");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 5);

            switch (opcion) {
                case 1:
                    programarCita();
                    break;
                case 2:
                    listarCitasPorMascota();
                    break;
                case 3:
                    listarTodasCitas();
                    break;
                case 4:
                    listarCitasDeHoy();
                    break;
                case 5:
                    buscarProximaCitaPendiente();
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

    private void programarCita() {
        System.out.println("\n----------------------------------------");
        System.out.println("            PROGRAMAR CITA");
        System.out.println("----------------------------------------");
        int mascotaId = ValidadorEntrada.leerInt("ID de la mascota: ");
        Mascota mascota = mascotaService.buscarMascota(mascotaId);
        if (mascota == null) {
            System.out.println("\n❌ Mascota no encontrada.");
            return;
        }

        System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
        String fechaStr = ValidadorEntrada.leerString("");
        String motivo = ValidadorEntrada.leerStringNoVacio("Motivo de la cita: ");
        System.out.print("ID del veterinario (opcional, Enter para omitir): ");
        String vetStr = ValidadorEntrada.leerString("");
        int estadoId = 1;

        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr.trim().replace(" ", "T"));
            Cita cita = new Cita(mascotaId, fechaHora, motivo, estadoId);
            if (!vetStr.isBlank()) {
                cita.setVeterinarioId(Integer.parseInt(vetStr.trim()));
            }

            if (citaService.registrarCita(cita)) {
                System.out.println("\n✅ Cita programada exitosamente.");
            } else {
                System.out.println("\n❌ No se pudo programar la cita.");
            }
        } catch (NumberFormatException e) {
            LoggerUtil.registrarError("ID de veterinario inválido al programar cita", e);
            System.out.println("\n❌ ID de veterinario inválido. Debe ser un número entero.");
        } catch (Exception e) {
            LoggerUtil.registrarError("Error al programar cita", e);
            System.out.println("\n❌ Error en el formato de fecha. Use YYYY-MM-DD HH:MM");
        }
    }

    private void listarCitasPorMascota() {
        System.out.println("\n----------------------------------------");
        System.out.println("          CITAS DE LA MASCOTA");
        System.out.println("----------------------------------------");
        int mascotaId = ValidadorEntrada.leerInt("ID de la mascota: ");
        List<Cita> citas = citaService.listarCitasPorMascota(mascotaId);
        imprimirTablaCitas(citas);
    }

    private void listarTodasCitas() {
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println("                                     TODAS LAS CITAS");
        List<Cita> citas = citaService.listarTodasCitas();
        imprimirTablaCitas(citas);
    }

    private void listarCitasDeHoy() {
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println("                                      CITAS DE HOY");
        List<Cita> citas = citaService.listarCitasDeHoy();
        imprimirTablaCitas(citas);
    }

    private void buscarProximaCitaPendiente() {
        System.out.println("\n----------------------------------------");
        System.out.println("        PRÓXIMA CITA PENDIENTE");
        System.out.println("----------------------------------------");
        int mascotaId = ValidadorEntrada.leerInt("ID de la mascota: ");
        citaService.proximaCitaPendiente(mascotaId).ifPresentOrElse(
                cita -> {
                    System.out.println("\n========================================");
                    System.out.println("          DETALLE DE LA CITA");
                    System.out.println("========================================");
                    System.out.printf("  ID Cita:      %d%n", cita.getId());
                    System.out.printf("  ID Mascota:   %d%n", cita.getMascotaId());
                    System.out.printf("  Veterinario:  %s%n", cita.getVeterinarioId() != null ? "ID " + cita.getVeterinarioId() : "Sin asignar");
                    System.out.printf("  Fecha / Hora: %s%n", cita.getFechaHora() != null ? cita.getFechaHora().toString().replace("T", " ") : "-");
                    System.out.printf("  Motivo:       %s%n", cita.getMotivo());
                    System.out.printf("  Estado:       %s%n", obtenerNombreEstado(cita.getEstadoId()));
                    System.out.println("========================================");
                },
                () -> System.out.println("\nNo hay citas pendientes para esta mascota.")
        );
    }

    private void imprimirTablaCitas(List<Cita> citas) {
        if (citas.isEmpty()) {
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("No se encontraron citas.");
            System.out.println("-----------------------------------------------------------------------------------------");
            return;
        }

        String separador = "+------+----------+--------+------------------+---------------------------+--------------+";
        System.out.println(separador);
        System.out.printf("| %-4s | %-8s | %-6s | %-16s | %-25s | %-12s |%n",
                "ID", "Mascota", "Vet ID", "Fecha / Hora", "Motivo", "Estado");
        System.out.println(separador);
        for (Cita c : citas) {
            String fechaStr = c.getFechaHora() != null ? c.getFechaHora().toString().replace("T", " ") : "-";
            if (fechaStr.length() > 16) {
                fechaStr = fechaStr.substring(0, 16);
            }
            System.out.printf("| %-4d | %-8d | %-6s | %-16s | %-25s | %-12s |%n",
                    c.getId(),
                    c.getMascotaId(),
                    c.getVeterinarioId() != null ? c.getVeterinarioId().toString() : "-",
                    fechaStr,
                    ValidadorEntrada.truncar(c.getMotivo(), 25),
                    obtenerNombreEstado(c.getEstadoId()));
        }
        System.out.println(separador);
        System.out.println("Total: " + citas.size() + " cita(s).");
    }

    private String obtenerNombreEstado(int estadoId) {
        switch (estadoId) {
            case 1: return "Programada";
            case 2: return "Confirmada";
            case 3: return "En Proceso";
            case 4: return "Finalizada";
            case 5: return "Cancelada";
            default: return "Estado " + estadoId;
        }
    }
}
