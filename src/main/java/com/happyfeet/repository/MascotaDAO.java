package com.happyfeet.repository;

import com.happyfeet.model.entities.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO extends OperacionesDAO {

    public boolean crear(Mascota mascota) throws SQLException {
        String sql = "INSERT INTO mascotas(dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, tatuaje, url_foto, alergias, condiciones_preexistentes, activo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mascota.getDuenoId());
            ps.setString(2, mascota.getNombre());
            ps.setInt(3, mascota.getRazaId());
            ps.setDate(4, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(5, mascota.getSexo());
            ps.setBigDecimal(6, mascota.getPesoActual());
            ps.setString(7, mascota.getMicrochip());
            ps.setString(8, mascota.getTatuaje());
            ps.setString(9, mascota.getUrlFoto());
            ps.setString(10, mascota.getAlergias());
            ps.setString(11, mascota.getCondicionesPreexistentes());
            ps.setBoolean(12, mascota.isActivo());
            return ps.executeUpdate() == 1;
        }
    }

    public Mascota buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM mascotas WHERE id = ?";
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

    public List<Mascota> listarPorDueno(int duenoId) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE dueno_id = ? AND activo = true ORDER BY nombre";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, duenoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public List<Mascota> listar() throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE activo = true ORDER BY nombre";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean actualizar(Mascota mascota) throws SQLException {
        String sql = "UPDATE mascotas SET dueno_id=?, nombre=?, raza_id=?, fecha_nacimiento=?, sexo=?, peso_actual=?, microchip=?, tatuaje=?, url_foto=?, alergias=?, condiciones_preexistentes=?, activo=? WHERE id=?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mascota.getDuenoId());
            ps.setString(2, mascota.getNombre());
            ps.setInt(3, mascota.getRazaId());
            ps.setDate(4, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(5, mascota.getSexo());
            ps.setBigDecimal(6, mascota.getPesoActual());
            ps.setString(7, mascota.getMicrochip());
            ps.setString(8, mascota.getTatuaje());
            ps.setString(9, mascota.getUrlFoto());
            ps.setString(10, mascota.getAlergias());
            ps.setString(11, mascota.getCondicionesPreexistentes());
            ps.setBoolean(12, mascota.isActivo());
            ps.setInt(13, mascota.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE mascotas SET activo = false WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean transferirPropiedad(int mascotaId, int nuevoDuenoId) throws SQLException {
        String sql = "UPDATE mascotas SET dueno_id = ? WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoDuenoId);
            ps.setInt(2, mascotaId);
            return ps.executeUpdate() == 1;
        }
    }

    private Mascota mapear(ResultSet rs) throws SQLException {
        Mascota mascota = new Mascota();
        mascota.setId(rs.getInt("id"));
        mascota.setDuenoId(rs.getInt("dueno_id"));
        mascota.setNombre(rs.getString("nombre"));
        mascota.setRazaId(rs.getInt("raza_id"));
        Date fecha = rs.getDate("fecha_nacimiento");
        mascota.setFechaNacimiento(fecha != null ? fecha.toLocalDate() : null);
        mascota.setSexo(rs.getString("sexo"));
        mascota.setPesoActual(rs.getBigDecimal("peso_actual"));
        mascota.setMicrochip(rs.getString("microchip"));
        mascota.setTatuaje(rs.getString("tatuaje"));
        mascota.setUrlFoto(rs.getString("url_foto"));
        mascota.setAlergias(rs.getString("alergias"));
        mascota.setCondicionesPreexistentes(rs.getString("condiciones_preexistentes"));
        mascota.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        mascota.setActivo(rs.getBoolean("activo"));
        return mascota;
    }
}
