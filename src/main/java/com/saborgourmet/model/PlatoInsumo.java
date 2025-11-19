package com.saborgourmet.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plato_insumo")
public class PlatoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlatoInsumo;

    @ManyToOne
    @JoinColumn(name = "id_plato")   // <-- corregido
    private Plato plato;

    @ManyToOne
    @JoinColumn(name = "id_insumo")  // <-- corregido
    private Insumo insumo;

    private double cantidadUsada;
}
