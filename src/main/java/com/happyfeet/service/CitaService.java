package com.happyfeet.service;

import com.happyfeet.model.entities.Cita;
import com.happyfeet.repository.CitaDAO;
import com.happyfeet.util.LoggerUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CitaService {

    private final CitaDAO citaDAO;

    public CitaService() {
        this.citaDAO = new CitaDAO();
    }

    public boolean registrarCita(Cita cita) {
        try {
            return citaDAO.crear(cita);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al registrar cita. Mascota ID: " + cita.getMascotaId(), e);
            return false;
        }
    }

    public Cita buscarCita(int id) {
        try {
            return citaDAO.buscarPorId(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al buscar cita. ID: " + id, e);
            return null;
        }
    }

    public boolean actualizarEstado(int citaId, int nuevoEstadoId) {
        try {
            return citaDAO.actualizarEstado(citaId, nuevoEstadoId);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al actualizar estado de cita. ID: " + citaId, e);
            return false;
        }
    }

    public List<Cita> listarCitasPorMascota(int mascotaId) {
        try {
            return citaDAO.listarPorMascota(mascotaId);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar citas de mascota. ID: " + mascotaId, e);
            return List.of();
        }
    }

    public List<Cita> listarTodasCitas() {
        try {
            return citaDAO.listar();
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar todas las citas", e);
            return List.of();
        }
    }

    // citas filtradas por estado
    public List<Cita> listarCitasPorEstado(int estadoId) {
        return listarTodasCitas().stream()
                .filter(c -> c.getEstadoId() == estadoId)
                .collect(Collectors.toList());
    }

    // citas agendadas para el dia de hoy ordenadas por hora
    public List<Cita> listarCitasDeHoy() {
        LocalDate hoy = LocalDate.now();
        return listarTodasCitas().stream()
                .filter(c -> c.getFechaHora().toLocalDate().equals(hoy))
                .sorted(Comparator.comparing(Cita::getFechaHora))
                .collect(Collectors.toList());
    }

    // busca la siguiente cita pendiente
    public Optional<Cita> proximaCitaPendiente(int mascotaId) {
        return listarCitasPorMascota(mascotaId).stream()
                .filter(c -> c.getEstadoId() == 1)
                .filter(c -> c.getFechaHora().isAfter(java.time.LocalDateTime.now()))
                .min(Comparator.comparing(Cita::getFechaHora));
    }

    // agrupa citas por cada veterinario
    public Map<Integer, List<Cita>> agruparCitasPorVeterinario() {
        return listarTodasCitas().stream()
                .filter(c -> c.getVeterinarioId() != null)
                .collect(Collectors.groupingBy(Cita::getVeterinarioId));
    }

    // conteo de citas de una mascota
    public long contarCitasPorMascota(int mascotaId) {
        return listarTodasCitas().stream()
                .filter(c -> c.getMascotaId() == mascotaId)
                .count();
    }
}
