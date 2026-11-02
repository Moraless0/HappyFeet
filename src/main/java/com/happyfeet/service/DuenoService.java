package com.happyfeet.service;

import com.happyfeet.model.entities.Dueno;
import com.happyfeet.repository.DuenoDAO;
import com.happyfeet.util.LoggerUtil;

import java.sql.SQLException;
import java.util.List;

public class DuenoService {
    private DuenoDAO duenoDAO;

    public DuenoService() {
        this.duenoDAO = new DuenoDAO();
    }

    public boolean registrarDueno(Dueno dueno) {
        try {
            if (duenoDAO.buscarPorDocumento(dueno.getDocumentoIdentidad()) != null) {
                LoggerUtil.registrarError("Ya existe un dueño con ese documento de identidad: " + dueno.getDocumentoIdentidad(), null);
                return false;
            }
            return duenoDAO.crear(dueno);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al registrar dueño", e);
            return false;
        }
    }

    public Dueno buscarDueno(int id) {
        try {
            return duenoDAO.buscarPorId(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al buscar dueño. ID: " + id, e);
            return null;
        }
    }

    public Dueno buscarPorDocumento(String documento) {
        try {
            return duenoDAO.buscarPorDocumento(documento);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al buscar dueño por documento: " + documento, e);
            return null;
        }
    }

    public List<Dueno> listarDuenos() {
        try {
            return duenoDAO.listar();
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar dueños", e);
            return List.of();
        }
    }

    public boolean actualizarDueno(Dueno dueno) {
        try {
            return duenoDAO.actualizar(dueno);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al actualizar dueño. ID: " + dueno.getId(), e);
            return false;
        }
    }

    public boolean eliminarDueno(int id) {
        try {
            return duenoDAO.eliminar(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al eliminar dueño. ID: " + id, e);
            return false;
        }
    }

    // busqueda por coincidencia en nombre usando streams
    public List<Dueno> buscarPorNombre(String nombre) {
        return listarDuenos().stream()
                .filter(d -> d.getNombreCompleto() != null && d.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    // listado de duenos activos ordenados alfabeticamente
    public List<Dueno> listarActivos() {
        return listarDuenos().stream()
                .filter(Dueno::isActivo)
                .sorted(java.util.Comparator.comparing(Dueno::getNombreCompleto, String.CASE_INSENSITIVE_ORDER))
                .collect(java.util.stream.Collectors.toList());
    }
}
