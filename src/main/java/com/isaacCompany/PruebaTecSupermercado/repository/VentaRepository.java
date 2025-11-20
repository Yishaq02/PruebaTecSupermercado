package com.isaacCompany.PruebaTecSupermercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isaacCompany.PruebaTecSupermercado.model.Venta;

public interface VentaRepository extends JpaRepository <Venta, Long> {

}
