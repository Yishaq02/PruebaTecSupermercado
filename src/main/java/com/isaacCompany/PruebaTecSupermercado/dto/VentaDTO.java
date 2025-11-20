package com.isaacCompany.PruebaTecSupermercado.dto;

import java.time.LocalDate;
import java.util.List;

import com.isaacCompany.PruebaTecSupermercado.model.EstadoVenta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    // datos de la venta
    private Long id;
    private LocalDate fecha;
    private EstadoVenta estado;

    // datos de la sucursal
    private Long idSucursal;

    // lista de detalles de la venta
    private List<DetalleVentaDTO> detalle;

    // total de la venta
    private Double total;

}
