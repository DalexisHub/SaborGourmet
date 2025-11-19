package com.saborgourmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank
    @Size(max = 8)
    private String dni;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    private String telefono;

    private String correo;

    private boolean estado = true;
}
