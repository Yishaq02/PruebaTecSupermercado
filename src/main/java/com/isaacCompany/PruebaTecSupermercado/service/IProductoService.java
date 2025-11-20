package com.isaacCompany.PruebaTecSupermercado.service;

import java.util.List;

import com.isaacCompany.PruebaTecSupermercado.dto.ProductoDTO;

public interface IProductoService {

    List<ProductoDTO> obtenerProductos();
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO);
    void eliminarProducto(Long id);

}
