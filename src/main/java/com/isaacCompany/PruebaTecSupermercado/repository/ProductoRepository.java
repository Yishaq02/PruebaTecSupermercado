package com.isaacCompany.PruebaTecSupermercado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isaacCompany.PruebaTecSupermercado.model.Producto;

public interface ProductoRepository extends JpaRepository <Producto, Long> {

    Optional<Producto> findByNombre(String nombre);
}
