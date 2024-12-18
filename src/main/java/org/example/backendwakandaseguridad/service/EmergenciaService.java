package org.example.backendwakandaseguridad.service;

import org.example.backendwakandaseguridad.model.ContactoEmergenciaDTO;
import org.example.backendwakandaseguridad.repos.ContactoEmergenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmergenciaService {
    private final ContactoEmergenciaRepository emergenciaRepository;

    public EmergenciaService(ContactoEmergenciaRepository emergenciaRepository) {
        this.emergenciaRepository = emergenciaRepository;
    }

    public List<ContactoEmergenciaDTO> listarContactos() {
        return emergenciaRepository.findAll().stream()
                .map(contacto -> new ContactoEmergenciaDTO(
                        contacto.getTipoServicio(),
                        contacto.getNumeroTelefono()))
                .collect(Collectors.toList());
    }
}
