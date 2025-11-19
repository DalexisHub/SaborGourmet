package com.saborgourmet.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "insumo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsumo;

    private String nombre;

    private String unidadMedida; // ej. kg, L, unidades

    private double stock;

    private double stockMinimo;

    private double precioCompra;

    private boolean estado = true; // activo/inactivo
}
