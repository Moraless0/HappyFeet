package com.happyfeet.repository;

import com.happyfeet.model.entities.Factura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO extends OperacionesDAO {

    public boolean crear(Factura factura) throws SQLException {
        String sql = "INSERT INTO facturas(dueno_id, numero_factura, fecha_emision, subtotal, impuesto, descuento, total, metodo_pago, estado, observaciones) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, factura.getDuenoId());
            ps.setString(2, factura.getNumeroFactura());
            ps.setTimestamp(3, Timestamp.valueOf(factura.getFechaEmision()));
            ps.setBigDecimal(4, factura.getSubtotal());
            ps.setBigDecimal(5, factura.getImpuesto());
            ps.setBigDecimal(6, factura.getDescuento());
            ps.setBigDecimal(7, factura.getTotal());
            ps.setString(8, factura.getMetodoPago() != null ? factura.getMetodoPago().name() : null);
            ps.setString(9, factura.getEstado() != null ? factura.getEstado().name() : null);
            ps.setString(10, factura.getObservaciones());
            return ps.executeUpdate() == 1;
        }
    }

    public Factura buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM facturas WHERE id = ?";
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

    public List<Factura> listarPorDueno(int duenoId) throws SQLException {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE dueno_id = ? ORDER BY fecha_emision DESC";
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

    public List<Factura> listar() throws SQLException {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas ORDER BY fecha_emision DESC";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Factura mapear(ResultSet rs) throws SQLException {
        Factura factura = new Factura();
        factura.setId(rs.getInt("id"));
        factura.setDuenoId(rs.getInt("dueno_id"));
        factura.setNumeroFactura(rs.getString("numero_factura"));
        factura.setFechaEmision(rs.getTimestamp("fecha_emision").toLocalDateTime());
        factura.setSubtotal(rs.getBigDecimal("subtotal"));
        factura.setImpuesto(rs.getBigDecimal("impuesto"));
        factura.setDescuento(rs.getBigDecimal("descuento"));
        factura.setTotal(rs.getBigDecimal("total"));
        String metodo = rs.getString("metodo_pago");
        if (metodo != null) {
            factura.setMetodoPago(com.happyfeet.model.enums.MetodoPago.valueOf(metodo));
        }
        String estado = rs.getString("estado");
        if (estado != null) {
            factura.setEstado(com.happyfeet.model.enums.EstadoFactura.valueOf(estado));
        }
        factura.setObservaciones(rs.getString("observaciones"));
        return factura;
    }
}
