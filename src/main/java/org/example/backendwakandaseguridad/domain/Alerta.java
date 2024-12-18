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

    private String tipo;
    private String descripcion;
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private EstadoAlerta estado;

    public Alerta(String tipo, String descripcion, LocalDateTime fechaHora, EstadoAlerta estado) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Alerta() {
    }
}
