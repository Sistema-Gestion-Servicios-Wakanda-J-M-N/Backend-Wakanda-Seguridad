package org.example.backendwakandaseguridad.service;

import org.example.backendwakandaseguridad.domain.Alerta;
import org.example.backendwakandaseguridad.domain.EstadoAlerta;
import org.example.backendwakandaseguridad.model.AlertaDTO;
import org.example.backendwakandaseguridad.repos.AlertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService {
    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<AlertaDTO> listarAlertas() {
        return alertaRepository.findAll().stream()
                .map(alerta -> new AlertaDTO(
                        alerta.getTipo(),
                        alerta.getDescripcion(),
                        alerta.getFechaHora(),
                        alerta.getEstado().name()))
                .collect(Collectors.toList());
    }

    public Alerta guardarAlerta(AlertaDTO alertaDTO) {
        Alerta alerta = new Alerta(
                alertaDTO.getTipo(),
                alertaDTO.getDescripcion(),
                alertaDTO.getFechaHora(),
                EstadoAlerta.valueOf(alertaDTO.getEstado())
        );
        return alertaRepository.save(alerta);
    }
}
