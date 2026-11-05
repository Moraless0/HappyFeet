package com.happyfeet.view;

import com.happyfeet.service.CitaService;
import com.happyfeet.util.LoggerUtil;
import com.happyfeet.util.ValidadorEntrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class MenuHorarios {

    private final CitaService citaService;

    public MenuHorarios() {
        this.citaService = new CitaService();
    }

    public void mostrarMenu() {
        System.out.println("\n----------------------------------------");
        System.out.println("   BUSCAR PRIMER HORARIO DISPONIBLE");
        System.out.println("----------------------------------------");

        LocalDate fecha = pedirFecha();

        Optional<LocalDateTime> resultado = citaService.buscarPrimerHorarioDisponible(fecha);
        if (resultado.isPresent()) {
            System.out.println("\nEl primer horario disponible para el día " + fecha
                    + " es a las " + resultado.get().toLocalTime());
        } else {
            System.out.println("\nNo hay horarios disponibles para el día " + fecha
                    + ". La agenda está completa.");
        }
    }

    private LocalDate pedirFecha() {
        while (true) {
            String entrada = ValidadorEntrada.leerString("Ingrese la fecha (AAAA-MM-DD): ").trim();
            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                LoggerUtil.mostrarError("Error: formato de fecha inválido. Use AAAA-MM-DD (ejemplo: 2026-11-05).");
            }
        }
    }
}
