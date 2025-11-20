package com.isaacCompany.PruebaTecSupermercado.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Entity;

@Getter
@Setter // Genera los metodos get y set
@NoArgsConstructor // Constructor vacio
@AllArgsConstructor // Constructor con todos los atributos
@Builder
@Entity
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;

}
