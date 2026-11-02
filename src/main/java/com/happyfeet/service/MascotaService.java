package com.happyfeet.service;

import com.happyfeet.model.entities.Mascota;
import com.happyfeet.repository.MascotaDAO;
import com.happyfeet.util.LoggerUtil;

import java.sql.SQLException;
import java.util.List;

public class MascotaService {
    private MascotaDAO mascotaDAO;

    public MascotaService() {
        this.mascotaDAO = new MascotaDAO();
    }

    public boolean registrarMascota(Mascota mascota) {
        try {
            return mascotaDAO.crear(mascota);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al registrar mascota. Nombre: " + mascota.getNombre(), e);
            return false;
        }
    }

    public Mascota buscarMascota(int id) {
        try {
            return mascotaDAO.buscarPorId(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al buscar mascota. ID: " + id, e);
            return null;
        }
    }

    public List<Mascota> listarMascotasPorDueno(int duenoId) {
        try {
            return mascotaDAO.listarPorDueno(duenoId);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar mascotas del dueño. ID: " + duenoId, e);
            return List.of();
        }
    }

    public List<Mascota> listarTodasMascotas() {
        try {
            return mascotaDAO.listar();
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar todas las mascotas", e);
            return List.of();
        }
    }

    public boolean actualizarMascota(Mascota mascota) {
        try {
            return mascotaDAO.actualizar(mascota);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al actualizar mascota. ID: " + mascota.getId(), e);
            return false;
        }
    }

    public boolean eliminarMascota(int id) {
        try {
            return mascotaDAO.eliminar(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al eliminar mascota. ID: " + id, e);
            return false;
        }
    }

    public boolean transferirPropiedad(int mascotaId, int nuevoDuenoId) {
        try {
            return mascotaDAO.transferirPropiedad(mascotaId, nuevoDuenoId);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al transferir propiedad. Mascota ID: " + mascotaId + ", Nuevo dueño ID: " + nuevoDuenoId, e);
            return false;
        }
    }

    // filtro de mascotas por nombre con streams
    public List<Mascota> buscarPorNombre(String nombre) {
        return listarTodasMascotas().stream()
                .filter(m -> m.getNombre() != null && m.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    // conteo de mascotas activas de un dueno
    public long contarMascotasPorDueno(int duenoId) {
        return listarTodasMascotas().stream()
                .filter(m -> m.getDuenoId() == duenoId && m.isActivo())
                .count();
    }
}
