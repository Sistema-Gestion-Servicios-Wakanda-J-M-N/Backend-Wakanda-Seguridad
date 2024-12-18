package org.example.backendwakandaseguridad.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContactoEmergencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoServicio;
    private String numeroTelefono;

    public ContactoEmergencia(String tipoServicio, String numeroTelefono) {
        this.tipoServicio = tipoServicio;
        this.numeroTelefono = numeroTelefono;
    }

    public ContactoEmergencia() {
    }
}
