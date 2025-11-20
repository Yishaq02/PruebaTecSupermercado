package com.isaacCompany.PruebaTecSupermercado.mapper;

import java.util.stream.Collectors;

import com.isaacCompany.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.isaacCompany.PruebaTecSupermercado.dto.ProductoDTO;
import com.isaacCompany.PruebaTecSupermercado.dto.SucursalDTO;
import com.isaacCompany.PruebaTecSupermercado.dto.VentaDTO;
import com.isaacCompany.PruebaTecSupermercado.model.Producto;
import com.isaacCompany.PruebaTecSupermercado.model.Sucursal;
import com.isaacCompany.PruebaTecSupermercado.model.Venta;

public class Mapper {

    // Mapeo de Producto a ProductoDTO y viceversa
    public static ProductoDTO toDTO(Producto p) {
        if (p == null)
            return null;
        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .build();
    }

    // Mapeo de Venta a VentaDTO y viceversa

    public static VentaDTO toDTO(Venta v) {
        if (v == null)
            return null;

        var detalle = v.getDetalle().stream().map(det -> DetalleVentaDTO.builder()
                .id(det.getProd().getId())
                .nombreProd(det.getProd().getNombre())
                .cantProd(det.getCantProd())
                .precio(det.getPrecio())
                .subtotal(det.getPrecio() * det.getCantProd())
                .build()).collect(Collectors.toList());

        var total = detalle.stream()
                .map(DetalleVentaDTO::getSubtotal) // Mapeo de Double a Double
                .reduce(0.0, Double::sum); // Reducción de Double a Double

        return VentaDTO.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .idSucursal(v.getSucursal().getId())
                .estado(v.getEstado())
                .detalle(detalle)
                .total(total)
                .build();
    }

    // Mapeo de Sucursal a SucursalDTO y viceversa

    public static SucursalDTO toDTO(Sucursal s) {
        if (s == null)
            return null;
        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();
    }
}
