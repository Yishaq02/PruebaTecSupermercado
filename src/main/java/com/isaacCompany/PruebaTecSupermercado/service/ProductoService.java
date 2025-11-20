package com.isaacCompany.PruebaTecSupermercado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isaacCompany.PruebaTecSupermercado.mapper.Mapper;
import com.isaacCompany.PruebaTecSupermercado.model.Producto;
import com.isaacCompany.PruebaTecSupermercado.dto.ProductoDTO;
import com.isaacCompany.PruebaTecSupermercado.exception.NotFoundException;
import com.isaacCompany.PruebaTecSupermercado.repository.ProductoRepository;

@Service
public class ProductoService implements IProductoService {

    @Autowired // Inyección de dependencia
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> obtenerProductos() {
        return productoRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = Producto.builder()
                .nombre(productoDTO.getNombre())
                .categoria(productoDTO.getCategoria())
                .precio(productoDTO.getPrecio())
                .cantidad(productoDTO.getCantidad())
                .build();
        return Mapper.toDTO(productoRepository.save(producto));

    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        // buscar si existe el producto
        Producto prod = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        prod.setNombre(productoDTO.getNombre());
        prod.setCategoria(productoDTO.getCategoria());
        prod.setCantidad(productoDTO.getCantidad());
        prod.setPrecio(productoDTO.getPrecio());

        return Mapper.toDTO(productoRepository.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
        // buscar si existe el producto
        if (!productoRepository.existsById(id)) {
            throw new NotFoundException("Producto no encontrado con id: " + id);
        }

        productoRepository.deleteById(id);
    }

}
