package org.example.backendwakandaseguridad.controller;



import org.example.backendwakandaseguridad.model.ContactoEmergenciaDTO;
import org.example.backendwakandaseguridad.service.EmergenciaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emergencias")
public class EmergenciaController {
    private final EmergenciaService emergenciaService;

    public EmergenciaController(EmergenciaService emergenciaService) {
        this.emergenciaService = emergenciaService;
    }

    @GetMapping
    public List<ContactoEmergenciaDTO> listarContactos() {
        return emergenciaService.listarContactos();
    }
}
