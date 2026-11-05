package com.happyfeet.repository;

import com.happyfeet.model.entities.Cita;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO extends OperacionesDAO {

    public boolean crear(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas(mascota_id, veterinario_id, fecha_hora, motivo, estado_id, observaciones) VALUES(?,?,?,?,?,?)";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cita.getMascotaId());
            ps.setObject(2, cita.getVeterinarioId());
            ps.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            ps.setString(4, cita.getMotivo());
            ps.setInt(5, cita.getEstadoId());
            ps.setString(6, cita.getObservaciones());
            return ps.executeUpdate() == 1;
        }
    }

    public Cita buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM citas WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        }
    }

    public List<Cita> listarPorMascota(int mascotaId) throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE mascota_id = ? ORDER BY fecha_hora DESC";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mascotaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public List<Cita> listarPorFecha(LocalDate fecha) throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE DATE(fecha_hora) = ? AND estado_id <> 5 ORDER BY fecha_hora ASC";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public List<Cita> listar() throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas ORDER BY fecha_hora DESC";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean actualizarEstado(int citaId, int nuevoEstadoId) throws SQLException {
        String sql = "UPDATE citas SET estado_id = ? WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstadoId);
            ps.setInt(2, citaId);
            return ps.executeUpdate() == 1;
        }
    }

    private Cita mapear(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        cita.setId(rs.getInt("id"));
        cita.setMascotaId(rs.getInt("mascota_id"));
        cita.setVeterinarioId((Integer) rs.getObject("veterinario_id"));
        cita.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        cita.setMotivo(rs.getString("motivo"));
        cita.setEstadoId(rs.getInt("estado_id"));
        cita.setObservaciones(rs.getString("observaciones"));
        cita.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        return cita;
    }
}
