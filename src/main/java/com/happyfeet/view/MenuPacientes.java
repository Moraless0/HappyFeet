package com.happyfeet.view;

import com.happyfeet.model.entities.Dueno;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.service.DuenoService;
import com.happyfeet.service.MascotaService;
import com.happyfeet.util.ValidadorEntrada;

import java.util.List;

public class MenuPacientes {
    private DuenoService duenoService;
    private MascotaService mascotaService;

    public MenuPacientes() {
        this.duenoService = new DuenoService();
        this.mascotaService = new MascotaService();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("          MÓDULO DE PACIENTES");
            System.out.println("========================================");
            System.out.println("  1. Registrar Dueño");
            System.out.println("  2. Listar Dueños");
            System.out.println("  3. Buscar Dueño por Documento");
            System.out.println("  4. Registrar Mascota");
            System.out.println("  5. Listar Mascotas de un Dueño");
            System.out.println("  6. Transferir Mascota");
            System.out.println("  0. Volver al Menú Principal");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 6);

            switch (opcion) {
                case 1:
                    registrarDueno();
                    break;
                case 2:
                    listarDuenos();
                    break;
                case 3:
                    buscarDuenoPorDocumento();
                    break;
                case 4:
                    registrarMascota();
                    break;
                case 5:
                    listarMascotasPorDueno();
                    break;
                case 6:
                    transferirMascota();
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

    private void registrarDueno() {
        System.out.println("\n----------------------------------------");
        System.out.println("         REGISTRAR NUEVO DUEÑO");
        System.out.println("----------------------------------------");
        String nombre = ValidadorEntrada.leerStringNoVacio("Nombre completo: ");
        String documento = ValidadorEntrada.leerStringNoVacio("Documento de identidad: ");
        String direccion = ValidadorEntrada.leerString("Dirección: ");
        String telefono = ValidadorEntrada.leerString("Teléfono: ");
        String email = ValidadorEntrada.leerStringNoVacio("Email: ");
        String contactoEmergencia = ValidadorEntrada.leerString("Contacto de emergencia: ");

        Dueno dueno = new Dueno(nombre, documento, email);
        dueno.setDireccion(direccion);
        dueno.setTelefono(telefono);
        dueno.setContactoEmergencia(contactoEmergencia);

        if (duenoService.registrarDueno(dueno)) {
            System.out.println("\n✅ Dueño registrado exitosamente.");
        } else {
            System.out.println("\n❌ No se pudo registrar el dueño.");
        }
    }

    private void listarDuenos() {
        System.out.println("\n---------------------------------------------------------------------------------------------------------");
        System.out.println("                                         LISTADO DE DUEÑOS");
        List<Dueno> duenos = duenoService.listarDuenos();
        if (duenos.isEmpty()) {
            System.out.println("---------------------------------------------------------------------------------------------------------");
            System.out.println("No hay dueños registrados actualmente.");
            System.out.println("---------------------------------------------------------------------------------------------------------");
            return;
        }

        String separador = "+------+------------------------+--------------+--------------+--------------------------+-----------------+";
        System.out.println(separador);
        System.out.printf("| %-4s | %-22s | %-12s | %-12s | %-24s | %-15s |%n",
                "ID", "Nombre", "Documento", "Teléfono", "Email", "Emergencia");
        System.out.println(separador);
        for (Dueno d : duenos) {
            System.out.printf("| %-4d | %-22s | %-12s | %-12s | %-24s | %-15s |%n",
                    d.getId(),
                    ValidadorEntrada.truncar(d.getNombreCompleto(), 22),
                    ValidadorEntrada.truncar(d.getDocumentoIdentidad(), 12),
                    ValidadorEntrada.truncar(d.getTelefono(), 12),
                    ValidadorEntrada.truncar(d.getEmail(), 24),
                    ValidadorEntrada.truncar(d.getContactoEmergencia(), 15));
        }
        System.out.println(separador);
        System.out.println("Total: " + duenos.size() + " dueño(s) registrado(s).");
    }

    private void buscarDuenoPorDocumento() {
        System.out.println("\n----------------------------------------");
        System.out.println("          BUSCAR DUEÑO POR DOC");
        System.out.println("----------------------------------------");
        String documento = ValidadorEntrada.leerStringNoVacio("Documento de identidad: ");
        Dueno dueno = duenoService.buscarPorDocumento(documento);
        if (dueno != null) {
            System.out.println("\n========================================");
            System.out.println("            DATOS DEL DUEÑO");
            System.out.println("========================================");
            System.out.printf("  ID:                  %d%n", dueno.getId());
            System.out.printf("  Nombre completo:     %s%n", dueno.getNombreCompleto());
            System.out.printf("  Documento:           %s%n", dueno.getDocumentoIdentidad());
            System.out.printf("  Dirección:           %s%n", ValidadorEntrada.texto(dueno.getDireccion()));
            System.out.printf("  Teléfono:            %s%n", ValidadorEntrada.texto(dueno.getTelefono()));
            System.out.printf("  Email:               %s%n", dueno.getEmail());
            System.out.printf("  Contacto Emergencia: %s%n", ValidadorEntrada.texto(dueno.getContactoEmergencia()));
            System.out.printf("  Estado:              %s%n", dueno.isActivo() ? "Activo" : "Inactivo");
            System.out.println("========================================");
        } else {
            System.out.println("\n❌ No se encontró ningún dueño con el documento: " + documento);
        }
    }

    private void registrarMascota() {
        System.out.println("\n----------------------------------------");
        System.out.println("        REGISTRAR NUEVA MASCOTA");
        System.out.println("----------------------------------------");
        int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");
        Dueno dueno = duenoService.buscarDueno(duenoId);
        if (dueno == null) {
            System.out.println("❌ Dueño no encontrado.");
            return;
        }

        String nombre = ValidadorEntrada.leerStringNoVacio("Nombre de la mascota: ");
        int razaId = ValidadorEntrada.leerInt("ID de la raza: ");
        String sexo = ValidadorEntrada.leerStringNoVacio("Sexo (Macho/Hembra): ");
        System.out.print("Fecha de nacimiento (YYYY-MM-DD) o Enter para omitir: ");
        String fechaStr = ValidadorEntrada.leerString("");
        System.out.print("Peso actual (kg) o Enter para omitir: ");
        String pesoStr = ValidadorEntrada.leerString("");

        Mascota mascota = new Mascota(duenoId, nombre, razaId, sexo);
        if (!fechaStr.isEmpty()) {
            try {
                mascota.setFechaNacimiento(java.time.LocalDate.parse(fechaStr.trim()));
            } catch (Exception e) {
                System.out.println("⚠️ Fecha inválida, se guardará sin fecha de nacimiento.");
            }
        }
        if (!pesoStr.isEmpty()) {
            try {
                mascota.setPesoActual(new java.math.BigDecimal(pesoStr.trim()));
            } catch (Exception e) {
                System.out.println("⚠️ Peso inválido, se omitirá.");
            }
        }

        if (mascotaService.registrarMascota(mascota)) {
            System.out.println("\n✅ Mascota registrada exitosamente.");
        } else {
            System.out.println("\n❌ No se pudo registrar la mascota.");
        }
    }

    private void listarMascotasPorDueno() {
        System.out.println("\n----------------------------------------");
        System.out.println("         MASCOTAS POR DUEÑO");
        System.out.println("----------------------------------------");
        int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");
        List<Mascota> mascotas = mascotaService.listarMascotasPorDueno(duenoId);
        if (mascotas.isEmpty()) {
            System.out.println("\nEl dueño no tiene mascotas registradas.");
            return;
        }

        String separador = "+------+--------------------+---------+---------+-----------+--------------+----------------------+";
        System.out.println("\n" + separador);
        System.out.printf("| %-4s | %-18s | %-7s | %-7s | %-9s | %-12s | %-20s |%n",
                "ID", "Nombre", "Raza", "Sexo", "Peso", "F. Nacim.", "Alergias");
        System.out.println(separador);
        for (Mascota m : mascotas) {
            String peso = m.getPesoActual() != null ? m.getPesoActual() + " kg" : "-";
            String nac = m.getFechaNacimiento() != null ? m.getFechaNacimiento().toString() : "-";
            System.out.printf("| %-4d | %-18s | %-7d | %-7s | %-9s | %-12s | %-20s |%n",
                    m.getId(),
                    ValidadorEntrada.truncar(m.getNombre(), 18),
                    m.getRazaId(),
                    ValidadorEntrada.truncar(m.getSexo(), 7),
                    ValidadorEntrada.truncar(peso, 9),
                    nac,
                    ValidadorEntrada.truncar(m.getAlergias(), 20));
        }
        System.out.println(separador);
        System.out.println("Total: " + mascotas.size() + " mascota(s).");
    }

    private void transferirMascota() {
        System.out.println("\n----------------------------------------");
        System.out.println("         TRANSFERIR PROPIEDAD");
        System.out.println("----------------------------------------");
        int mascotaId = ValidadorEntrada.leerInt("ID de la mascota: ");
        int nuevoDuenoId = ValidadorEntrada.leerInt("ID del nuevo dueño: ");

        if (mascotaService.transferirPropiedad(mascotaId, nuevoDuenoId)) {
            System.out.println("\n✅ Propiedad transferida exitosamente.");
        } else {
            System.out.println("\n❌ No se pudo transferir la propiedad. Verifique los IDs ingresados.");
        }
    }
}
