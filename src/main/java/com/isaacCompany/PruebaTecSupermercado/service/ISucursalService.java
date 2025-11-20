package com.isaacCompany.PruebaTecSupermercado.service;

import java.util.List;

import com.isaacCompany.PruebaTecSupermercado.dto.SucursalDTO;

public interface ISucursalService {

    List<SucursalDTO> obtenerSucursales();
    SucursalDTO crearSucursal(SucursalDTO sucursalDTO);
    SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDTO);
    void eliminarSucursal(Long id);
}
