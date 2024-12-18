package org.example.backendwakandaseguridad.controller;

import org.example.backendwakandaseguridad.model.AlertaDTO;
import org.example.backendwakandaseguridad.service.AlertaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {
    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping
    public List<AlertaDTO> listarAlertas() {
        return alertaService.listarAlertas();
    }


}
