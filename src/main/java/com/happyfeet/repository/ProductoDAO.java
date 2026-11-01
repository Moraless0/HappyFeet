package com.happyfeet.repository;

import com.happyfeet.model.entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends OperacionesDAO {

    public boolean crear(Producto producto) throws SQLException {
        String sql = "INSERT INTO inventario(nombre_producto, producto_tipo_id, descripcion, fabricante, proveedor_id, lote, cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento, precio_compra, precio_venta, requiere_receta, activo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setInt(2, producto.getProductoTipoId());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getFabricante());
            ps.setObject(5, producto.getProveedorId());
            ps.setString(6, producto.getLote());
            ps.setInt(7, producto.getCantidadStock());
            ps.setInt(8, producto.getStockMinimo());
            ps.setString(9, producto.getUnidadMedida());
            ps.setDate(10, producto.getFechaVencimiento() != null ? Date.valueOf(producto.getFechaVencimiento()) : null);
            ps.setBigDecimal(11, producto.getPrecioCompra());
            ps.setBigDecimal(12, producto.getPrecioVenta());
            ps.setBoolean(13, producto.isRequiereReceta());
            ps.setBoolean(14, producto.isActivo());
            return ps.executeUpdate() == 1;
        }
    }

    public Producto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM inventario WHERE id = ?";
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

    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE activo = true ORDER BY nombre_producto";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Producto> listarPorTipo(int tipoId) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE producto_tipo_id = ? AND activo = true ORDER BY nombre_producto";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tipoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public List<Producto> listarStockBajo() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE cantidad_stock <= stock_minimo AND activo = true ORDER BY cantidad_stock";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Producto> listarProximosAVencer(int dias) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE fecha_vencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) AND activo = true ORDER BY fecha_vencimiento";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dias);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE inventario SET nombre_producto=?, producto_tipo_id=?, descripcion=?, fabricante=?, proveedor_id=?, lote=?, cantidad_stock=?, stock_minimo=?, unidad_medida=?, fecha_vencimiento=?, precio_compra=?, precio_venta=?, requiere_receta=?, activo=? WHERE id=?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setInt(2, producto.getProductoTipoId());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getFabricante());
            ps.setObject(5, producto.getProveedorId());
            ps.setString(6, producto.getLote());
            ps.setInt(7, producto.getCantidadStock());
            ps.setInt(8, producto.getStockMinimo());
            ps.setString(9, producto.getUnidadMedida());
            ps.setDate(10, producto.getFechaVencimiento() != null ? Date.valueOf(producto.getFechaVencimiento()) : null);
            ps.setBigDecimal(11, producto.getPrecioCompra());
            ps.setBigDecimal(12, producto.getPrecioVenta());
            ps.setBoolean(13, producto.isRequiereReceta());
            ps.setBoolean(14, producto.isActivo());
            ps.setInt(15, producto.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean actualizarStock(int productoId, int cantidad) throws SQLException {
        String sql = "UPDATE inventario SET cantidad_stock = cantidad_stock + ? WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, productoId);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "UPDATE inventario SET activo = false WHERE id = ?";
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombreProducto(rs.getString("nombre_producto"));
        producto.setProductoTipoId(rs.getInt("producto_tipo_id"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setFabricante(rs.getString("fabricante"));
        producto.setProveedorId((Integer) rs.getObject("proveedor_id"));
        producto.setLote(rs.getString("lote"));
        producto.setCantidadStock(rs.getInt("cantidad_stock"));
        producto.setStockMinimo(rs.getInt("stock_minimo"));
        producto.setUnidadMedida(rs.getString("unidad_medida"));
        Date fecha = rs.getDate("fecha_vencimiento");
        producto.setFechaVencimiento(fecha != null ? fecha.toLocalDate() : null);
        producto.setPrecioCompra(rs.getBigDecimal("precio_compra"));
        producto.setPrecioVenta(rs.getBigDecimal("precio_venta"));
        producto.setRequiereReceta(rs.getBoolean("requiere_receta"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        return producto;
    }
}
