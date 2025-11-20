package com.isaacCompany.PruebaTecSupermercado.service;

import java.time.LocalDate;

import com.isaacCompany.PruebaTecSupermercado.dto.ProductoMasVendidoDTO;

public interface IEstadisticasService {

    ProductoMasVendidoDTO productoMasVendido(LocalDate fecha, Long sucursalId);

}
