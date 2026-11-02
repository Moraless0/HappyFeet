package com.happyfeet.service;

import com.happyfeet.model.entities.Factura;
import com.happyfeet.model.enums.EstadoFactura;
import com.happyfeet.model.enums.MetodoPago;
import com.happyfeet.repository.FacturaDAO;
import com.happyfeet.util.LoggerUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FacturaService {
    private FacturaDAO facturaDAO;
    private InventarioService inventarioService;

    public FacturaService() {
        this.facturaDAO = new FacturaDAO();
        this.inventarioService = new InventarioService();
    }

    public Factura crearFactura(int duenoId, BigDecimal subtotal) {
        String numeroFactura = generarNumeroFactura();
        Factura factura = new Factura(duenoId, numeroFactura, subtotal, subtotal);
        factura.setEstado(EstadoFactura.PENDIENTE);
        return factura;
    }

    public boolean procesarPago(Factura factura, MetodoPago metodoPago, int puntosClub) {
        return procesarPago(factura, metodoPago, puntosClub, null);
    }

    public boolean procesarPago(Factura factura, MetodoPago metodoPago, int puntosClub, java.util.Map<Integer, Integer> productosUsados) {
        try {
            EstrategiaPago estrategia;

            switch (metodoPago) {
                case EFECTIVO:
                    estrategia = new PagoEfectivo();
                    break;
                case TARJETA:
                    estrategia = new PagoTarjeta();
                    break;
                case TRANSFERENCIA:
                    estrategia = new PagoEfectivo();
                    break;
                default:
                    estrategia = new PagoEfectivo();
            }

            BigDecimal montoFinal = estrategia.procesarPago(factura.getTotal());

            if (puntosClub > 0 && metodoPago == MetodoPago.TARJETA) {
                PagoClub pagoClub = new PagoClub(puntosClub);
                montoFinal = pagoClub.procesarPago(factura.getTotal());
            }

            factura.setTotal(montoFinal);
            factura.setMetodoPago(metodoPago);
            factura.setEstado(EstadoFactura.PAGADA);

            boolean resultado = facturaDAO.crear(factura);

            if (resultado && productosUsados != null) {
                // descontamos del inventario los productos usados
                productosUsados.forEach((productoId, cantidad) ->
                        inventarioService.descontarStock(productoId, cantidad)
                );
            }

            return resultado;
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al procesar pago. Factura: " + factura.getNumeroFactura(), e);
            return false;
        }
    }

    public boolean generarFacturaArchivo(Factura factura) {
        try {
            Comprobante comprobante = GeneradorComprobante.crearComprobante("factura", factura);
            String nombreArchivo = "factura_" + factura.getNumeroFactura() + ".txt";
            comprobante.generar(nombreArchivo);
            return true;
        } catch (Exception e) {
            LoggerUtil.registrarError("Error al generar factura. Factura: " + factura.getNumeroFactura(), e);
            return false;
        }
    }

    public Factura buscarFactura(int id) {
        try {
            return facturaDAO.buscarPorId(id);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al buscar factura. ID: " + id, e);
            return null;
        }
    }

    public List<Factura> listarFacturasPorDueno(int duenoId) {
        try {
            return facturaDAO.listarPorDueno(duenoId);
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar facturas del dueño. ID: " + duenoId, e);
            return List.of();
        }
    }

    public List<Factura> listarTodasFacturas() {
        try {
            return facturaDAO.listar();
        } catch (SQLException e) {
            LoggerUtil.registrarError("Error al listar todas las facturas", e);
            return List.of();
        }
    }

    private String generarNumeroFactura() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "FAC-" + fecha + "-" + uuid;
    }

    // total recaudado de facturas cobradas
    public BigDecimal calcularTotalRecaudado() {
        return listarTodasFacturas().stream()
                .filter(f -> f.getEstado() == com.happyfeet.model.enums.EstadoFactura.PAGADA)
                .map(Factura::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // facturas que siguen pendientes
    public List<Factura> listarFacturasPendientes() {
        return listarTodasFacturas().stream()
                .filter(f -> f.getEstado() == com.happyfeet.model.enums.EstadoFactura.PENDIENTE)
                .sorted(Comparator.comparing(Factura::getFechaEmision))
                .collect(Collectors.toList());
    }

    // agrupa facturas segun el medio de pago
    public Map<com.happyfeet.model.enums.MetodoPago, List<Factura>> agruparPorMetodoPago() {
        return listarTodasFacturas().stream()
                .filter(f -> f.getMetodoPago() != null)
                .collect(Collectors.groupingBy(Factura::getMetodoPago));
    }
}
