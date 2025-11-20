package com.isaacCompany.PruebaTecSupermercado.service;

import java.util.List;

import com.isaacCompany.PruebaTecSupermercado.dto.VentaDTO;

public interface IVentaService {

    List<VentaDTO> obtenerVentas();
    VentaDTO crearVenta(VentaDTO ventaDTO);
    VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO);
    void eliminarVenta(Long id);

}
