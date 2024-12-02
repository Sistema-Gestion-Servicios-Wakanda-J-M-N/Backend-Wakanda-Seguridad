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
                .map(alerta -> {
                    AlertaDTO dto = new AlertaDTO();
                    dto.setTipo(alerta.getTipo());
                    dto.setDescripcion(alerta.getDescripcion());
                    dto.setFechaHora(alerta.getFechaHora());
                    dto.setEstado(alerta.getEstado().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Alerta guardarAlerta(AlertaDTO alertaDTO) {
        Alerta alerta = new Alerta();
        alerta.setTipo(alertaDTO.getTipo());
        alerta.setDescripcion(alertaDTO.getDescripcion());
        alerta.setFechaHora(alertaDTO.getFechaHora());
        alerta.setEstado(EstadoAlerta.valueOf(alertaDTO.getEstado())); // Convertir String a Enum
        return alertaRepository.save(alerta);
    }
}
