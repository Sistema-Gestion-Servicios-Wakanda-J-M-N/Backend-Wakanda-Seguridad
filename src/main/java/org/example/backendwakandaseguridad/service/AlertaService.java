package org.example.backendwakandaseguridad.service;

import org.example.backendwakandaseguridad.domain.Alerta;
import org.example.backendwakandaseguridad.model.AlertaDTO;
import org.example.backendwakandaseguridad.repos.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService {
    private final AlertaRepository alertaRepository;

    @Autowired
    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<AlertaDTO> listarAlertas() {
        return alertaRepository.findAll().stream()
                .map(alerta -> {
                    AlertaDTO dto = new AlertaDTO();
                    dto.setTipo(alerta.getTipo());
                    dto.setDescripcion(alerta.getDescripcion());
                    dto.setFechaHora(alerta.getFechaHora());
                    dto.setEstado(alerta.getEstado() != null ? alerta.getEstado().name() : "ACTIVA");
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
