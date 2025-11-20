package com.isaacCompany.PruebaTecSupermercado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaacCompany.PruebaTecSupermercado.dto.SucursalDTO;
import com.isaacCompany.PruebaTecSupermercado.exception.NotFoundException;
import com.isaacCompany.PruebaTecSupermercado.mapper.Mapper;
import com.isaacCompany.PruebaTecSupermercado.model.Sucursal;
import com.isaacCompany.PruebaTecSupermercado.repository.SucursalRepository;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<SucursalDTO> obtenerSucursales() {
        return sucursalRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDTO) {
        Sucursal sucursal = Sucursal.builder()
                .nombre(sucursalDTO.getNombre())
                .direccion(sucursalDTO.getDireccion())
                .build();

        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDTO) {
        // existe la sucursal
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setDireccion(sucursalDTO.getDireccion());
        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public void eliminarSucursal(Long id) {
        // buscar si existe la sucursal
        if (!sucursalRepository.existsById(id)) {
            throw new NotFoundException("Sucursal no encontrada con id: " + id);
        }

        sucursalRepository.deleteById(id);
    }

}
