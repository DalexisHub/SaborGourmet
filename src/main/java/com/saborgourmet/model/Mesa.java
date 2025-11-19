package com.saborgourmet.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;

    private int numero;

    private int capacidad;

    @Column(length = 20, nullable = false)
    private String estado = "disponible"; // disponible, ocupada, reservada, mantenimiento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente")
    private Cliente cliente; // cliente asignado (puede ser null si está libre)
}
