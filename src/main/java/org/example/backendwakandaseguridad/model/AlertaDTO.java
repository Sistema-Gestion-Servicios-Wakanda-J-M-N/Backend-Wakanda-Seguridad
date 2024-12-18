package org.example.backendwakandaseguridad.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertaDTO {
    private String tipo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String estado;

    public AlertaDTO(String tipo, String descripcion, LocalDateTime fechaHora, String name) {
    }
}
