package com.happyfeet.service;

import com.happyfeet.model.entities.Producto;
import com.happyfeet.repository.ProductoDAO;
import com.happyfeet.util.LoggerUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InventarioService {
    private final ProductoDAO productoDAO;

    public InventarioService() {
        this.productoDAO = new ProductoDAO();
    }

    public boolean registrarProducto(Producto producto) {
        try {
            return productoDAO.crear(producto);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al registrar producto", e);
            LoggerUtil.mostrarError("Error al registrar producto en la base de datos.");
            return false;
        }
    }

    public Producto buscarProducto(int id) {
        try {
            return productoDAO.buscarPorId(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al buscar producto", e);
            LoggerUtil.mostrarError("Error al buscar producto en la base de datos.");
            return null;
        }
    }

    public List<Producto> listarProductos() {
        try {
            return productoDAO.listar();
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar productos", e);
            LoggerUtil.mostrarError("Error al listar productos en la base de datos.");
            return List.of();
        }
    }

    // productos con stock menor o igual al minimo
    public List<Producto> reporteStockBajo() {
        return listarProductos().stream()
                .filter(p -> p.getCantidadStock() <= p.getStockMinimo())
                .sorted(Comparator.comparingInt(Producto::getCantidadStock))
                .collect(Collectors.toList());
    }

    // productos por vencer dentro del rango de dias indicado
    public List<Producto> reporteProximosAVencer(int dias) {
        LocalDate limite = LocalDate.now().plusDays(dias);
        return listarProductos().stream()
                .filter(p -> p.getFechaVencimiento() != null)
                .filter(p -> !p.getFechaVencimiento().isBefore(LocalDate.now()))
                .filter(p -> !p.getFechaVencimiento().isAfter(limite))
                .sorted(Comparator.comparing(Producto::getFechaVencimiento))
                .collect(Collectors.toList());
    }

    // valor estimado de todo el stock multiplicado por su precio
    public BigDecimal calcularValorTotalInventario() {
        return listarProductos().stream()
                .filter(p -> p.getPrecioVenta() != null)
                .map(p -> p.getPrecioVenta().multiply(BigDecimal.valueOf(p.getCantidadStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // clasificacion por tipo de producto
    public Map<Integer, List<Producto>> agruparPorTipo() {
        return listarProductos().stream()
                .collect(Collectors.groupingBy(Producto::getProductoTipoId));
    }

    // producto con mayor existencia en bodega
    public Optional<Producto> productoConMayorStock() {
        return listarProductos().stream()
                .max(Comparator.comparingInt(Producto::getCantidadStock));
    }

    // filtro por tipo de producto
    public List<Producto> filtrarPorTipoConStream(int tipoId) {
        return listarProductos().stream()
                .filter(p -> p.getProductoTipoId() == tipoId)
                .collect(Collectors.toList());
    }

    @Deprecated
    public List<Producto> listarStockBajo() {
        try {
            return productoDAO.listarStockBajo();
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar productos con stock bajo", e);
            return List.of();
        }
    }

    @Deprecated
    public List<Producto> listarProximosAVencer(int dias) {
        try {
            return productoDAO.listarProximosAVencer(dias);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar próximos a vencer", e);
            return List.of();
        }
    }

    public boolean actualizarStock(int productoId, int cantidad) {
        try {
            Producto producto = productoDAO.buscarPorId(productoId);
            if (producto == null) {
                LoggerUtil.mostrarError("Producto no encontrado.");
                return false;
            }

            int nuevoStock = producto.getCantidadStock() + cantidad;
            if (nuevoStock < 0) {
                LoggerUtil.mostrarError("Stock insuficiente. Stock actual: " + producto.getCantidadStock());
                return false;
            }

            return productoDAO.actualizarStock(productoId, cantidad);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al actualizar stock", e);
            LoggerUtil.mostrarError("Error al actualizar stock en la base de datos.");
            return false;
        }
    }

    public boolean validarStock(int productoId, int cantidadRequerida) {
        try {
            Producto producto = productoDAO.buscarPorId(productoId);
            if (producto == null) {
                return false;
            }
            return producto.getCantidadStock() >= cantidadRequerida;
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al validar stock", e);
            return false;
        }
    }

    public boolean validarVencimiento(int productoId) {
        try {
            Producto producto = productoDAO.buscarPorId(productoId);
            if (producto == null || producto.getFechaVencimiento() == null) {
                return true;
            }
            return producto.getFechaVencimiento().isAfter(LocalDate.now());
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al validar vencimiento", e);
            return false;
        }
    }

    public boolean descontarStock(int productoId, int cantidad) {
        try {
            if (!validarStock(productoId, cantidad)) {
                LoggerUtil.mostrarError("Stock insuficiente para descontar.");
                return false;
            }
            if (!validarVencimiento(productoId)) {
                LoggerUtil.mostrarError("Producto vencido. No se puede usar.");
                return false;
            }
            return productoDAO.actualizarStock(productoId, -cantidad);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al descontar stock", e);
            LoggerUtil.mostrarError("Error al descontar stock en la base de datos.");
            return false;
        }
    }
}
