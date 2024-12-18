package org.example.backendwakandaseguridad.model;

import lombok.Data;

@Data
public class ContactoEmergenciaDTO {
    private String tipoServicio;
    private String numeroTelefono;

    public ContactoEmergenciaDTO(String tipoServicio, String numeroTelefono) {
    }
}
