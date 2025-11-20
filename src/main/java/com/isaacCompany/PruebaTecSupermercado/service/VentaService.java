package com.isaacCompany.PruebaTecSupermercado.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaacCompany.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.isaacCompany.PruebaTecSupermercado.dto.VentaDTO;
import com.isaacCompany.PruebaTecSupermercado.exception.NotFoundException;
import com.isaacCompany.PruebaTecSupermercado.mapper.Mapper;
import com.isaacCompany.PruebaTecSupermercado.model.DetalleVenta;
import com.isaacCompany.PruebaTecSupermercado.model.Producto;
import com.isaacCompany.PruebaTecSupermercado.model.Sucursal;
import com.isaacCompany.PruebaTecSupermercado.model.Venta;
import com.isaacCompany.PruebaTecSupermercado.repository.ProductoRepository;
import com.isaacCompany.PruebaTecSupermercado.repository.SucursalRepository;
import com.isaacCompany.PruebaTecSupermercado.repository.VentaRepository;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<VentaDTO> obtenerVentas() {

        List<Venta> ventas = ventaRepository.findAll();
        List<VentaDTO> ventaDTOs = new ArrayList<>();
        VentaDTO dto;

        for (Venta v : ventas) {
            dto = Mapper.toDTO(v);
            ventaDTOs.add(dto);
        }
        return ventaDTOs;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {

        // Validaciones
        if (ventaDto == null)
            throw new RuntimeException("VentaDTO es null");
        if (ventaDto.getIdSucursal() == null)
            throw new RuntimeException("Debe indicar la sucursal");
        if (ventaDto.getDetalle() == null || ventaDto.getDetalle().isEmpty())
            throw new RuntimeException("Debe incluir al menos un producto");

        // Buscar la sucursal
        Sucursal suc = sucursalRepository.findById(ventaDto.getIdSucursal()).orElse(null);
        if (suc == null) {
            throw new NotFoundException("Sucursal no encontrada");
        }

        // Crear la venta
        Venta vent = new Venta();
        vent.setFecha(ventaDto.getFecha());
        vent.setEstado(ventaDto.getEstado());
        vent.setSucursal(suc);
        vent.setTotal(ventaDto.getTotal());

        // La lista de detalles
        // --> Acá están los productos
        List<DetalleVenta> detalles = new ArrayList<>();
        Double totalCalculado = 0.0;

        for (DetalleVentaDTO detDTO : ventaDto.getDetalle()) {
            // Buscar producto por id (tu detDTO usa id como id de producto)
            Producto p = productoRepository.findByNombre(detDTO.getNombreProd()).orElse(null);
            if (p == null) {
                throw new RuntimeException("Producto no encontrado: " + detDTO.getNombreProd());
            }

            // Crear detalle
            DetalleVenta detalleVent = new DetalleVenta();
            detalleVent.setProd(p);
            detalleVent.setPrecio(detDTO.getPrecio());
            detalleVent.setCantProd(detDTO.getCantProd());
            detalleVent.setVenta(vent);

            detalles.add(detalleVent);
            totalCalculado = totalCalculado + (detDTO.getPrecio() * detDTO.getCantProd());

        }
        // Seteamos la lista de detalle Venta
        vent.setDetalle(detalles);

        // guardamos en la BD
        vent = ventaRepository.save(vent);

        // Mapeo de salida
        VentaDTO ventaSalida = Mapper.toDTO(vent);

        return ventaSalida;
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        // verificar si existe la venta
        Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }

        if (ventaDTO.getFecha() != null) {
            venta.setFecha(ventaDTO.getFecha());
        }
        if (ventaDTO.getEstado() != null) {
            venta.setEstado(ventaDTO.getEstado());
        }
        if (ventaDTO.getTotal() != null) {
            venta.setTotal(ventaDTO.getTotal());
        }
        if (ventaDTO.getIdSucursal() != null) {
            Sucursal suc = sucursalRepository.findById(ventaDTO.getIdSucursal()).orElse(null);
            if (suc == null)
                throw new NotFoundException("Sucursal no encontrada");
            venta.setSucursal(suc);
        }
        ventaRepository.save(venta);

        VentaDTO ventaSalida = Mapper.toDTO(venta);

        return ventaSalida;
    }

    @Override
    public void eliminarVenta(Long id) {
        // buscar si existe la venta

        Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }

        ventaRepository.delete(venta);

    }

}
