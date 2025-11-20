package com.isaacCompany.PruebaTecSupermercado.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaacCompany.PruebaTecSupermercado.dto.ProductoMasVendidoDTO;
import com.isaacCompany.PruebaTecSupermercado.model.DetalleVenta;
import com.isaacCompany.PruebaTecSupermercado.model.EstadoVenta;
import com.isaacCompany.PruebaTecSupermercado.model.Producto;
import com.isaacCompany.PruebaTecSupermercado.repository.VentaRepository;

@Service
public class EstadisticasService implements IEstadisticasService {

    @Autowired
    private VentaRepository ventaRepository;

    public ProductoMasVendidoDTO productoMasVendido(LocalDate fecha, Long sucursalId) {

        List<DetalleVenta> detalles = ventaRepository.findAll().stream()
                .filter(venta -> venta.getEstado() == EstadoVenta.REGISTRADA)
                .filter(venta -> fecha == null || venta.getFecha().equals(fecha))
                .filter(venta -> sucursalId == null || venta.getSucursal().getId().equals(sucursalId))
                .flatMap(venta -> venta.getDetalle().stream())
                .collect(Collectors.toList());

        Map<Producto, Integer> productoConCantidad = detalles.stream()
                .collect(Collectors.groupingBy(
                        DetalleVenta::getProd,
                        Collectors.summingInt(DetalleVenta::getCantProd)));

        Map.Entry<Producto, Integer> productoMasVendido = productoConCantidad.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);

        if (productoMasVendido == null) {
            return null;
        }

        Producto producto = productoMasVendido.getKey();
        Integer cantidadTotal = productoMasVendido.getValue();

        return ProductoMasVendidoDTO.builder()
                .productoId(producto.getId())
                .nombreProducto(producto.getNombre())
                .categoria(producto.getCategoria())
                .cantidadTotal(cantidadTotal)
                .build();
    }
}
