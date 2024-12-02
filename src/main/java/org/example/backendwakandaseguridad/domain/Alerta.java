package org.example.backendwakandaseguridad.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // Ej: Incendio, Desastre Natural
    private String descripcion;
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private EstadoAlerta estado; // ACTIVA, RESUELTA
}

