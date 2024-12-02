package org.example.backendwakandaseguridad.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContactoEmergencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoServicio; // Ej: Policía, Ambulancia, Bomberos
    private String numeroTelefono;
}
