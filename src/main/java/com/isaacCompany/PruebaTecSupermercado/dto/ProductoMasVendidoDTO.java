package com.isaacCompany.PruebaTecSupermercado.dto;

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
public class ProductoMasVendidoDTO {
    private Long productoId;
    private String nombreProducto;
    private String categoria;
    private Integer cantidadTotal;
}
