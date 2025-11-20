package com.isaacCompany.PruebaTecSupermercado.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isaacCompany.PruebaTecSupermercado.dto.ProductoMasVendidoDTO;
import com.isaacCompany.PruebaTecSupermercado.service.EstadisticasService;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoMasVendidoDTO> productoMasVendido(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Long sucursalId) {

        ProductoMasVendidoDTO resultado = estadisticasService.productoMasVendido(fecha, sucursalId);

        if (resultado == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }
}
