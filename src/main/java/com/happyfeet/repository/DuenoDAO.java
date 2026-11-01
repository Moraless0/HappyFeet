package com.happyfeet.repository;

import com.happyfeet.model.entities.Dueno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DuenoDAO extends OperacionesDAO {

    public boolean crear(Dueno dueno) throws SQLException {
        String sql = "INSERT INTO duenos(nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia, activo) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dueno.getNombreCompleto());
            ps.setString(2, dueno.getDocumentoIdentidad());
            ps.setString(3, dueno.getDireccion());
            ps.setString(4, dueno.getTelefono());
            ps.setString(5, dueno.getEmail());
            ps.setString(6, dueno.getContactoEmergencia());
            ps.setBoolean(7, dueno.isActivo());
            return ps.executeUpdate() == 1;
        }
    }

    public Dueno buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM duenos WHERE id = ?";
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

    public Dueno buscarPorDocumento(String documento) throws SQLException {
        String sql = "SELECT * FROM duenos WHERE documento_identidad = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        }
    }

    public List<Dueno> listar() throws SQLException {
        List<Dueno> lista = new ArrayList<>();
        String sql = "SELECT * FROM duenos WHERE activo = true ORDER BY nombre_completo";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean actualizar(Dueno dueno) throws SQLException {
        String sql = "UPDATE duenos SET nombre_completo=?, documento_identidad=?, direccion=?, telefono=?, email=?, contacto_emergencia=?, activo=? WHERE id=?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dueno.getNombreCompleto());
            ps.setString(2, dueno.getDocumentoIdentidad());
            ps.setString(3, dueno.getDireccion());
            ps.setString(4, dueno.getTelefono());
            ps.setString(5, dueno.getEmail());
            ps.setString(6, dueno.getContactoEmergencia());
            ps.setBoolean(7, dueno.isActivo());
            ps.setInt(8, dueno.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE duenos SET activo = false WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Dueno mapear(ResultSet rs) throws SQLException {
        Dueno dueno = new Dueno();
        dueno.setId(rs.getInt("id"));
        dueno.setNombreCompleto(rs.getString("nombre_completo"));
        dueno.setDocumentoIdentidad(rs.getString("documento_identidad"));
        dueno.setDireccion(rs.getString("direccion"));
        dueno.setTelefono(rs.getString("telefono"));
        dueno.setEmail(rs.getString("email"));
        dueno.setContactoEmergencia(rs.getString("contacto_emergencia"));
        dueno.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        dueno.setActivo(rs.getBoolean("activo"));
        return dueno;
    }
}
