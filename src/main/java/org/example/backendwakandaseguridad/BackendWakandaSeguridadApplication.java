package org.example.backendwakandaseguridad;

import org.example.backendwakandaseguridad.domain.Alerta;
import org.example.backendwakandaseguridad.domain.ContactoEmergencia;
import org.example.backendwakandaseguridad.domain.EstadoAlerta;
import org.example.backendwakandaseguridad.model.AlertaDTO;
import org.example.backendwakandaseguridad.model.ContactoEmergenciaDTO;
import org.example.backendwakandaseguridad.repos.AlertaRepository;
import org.example.backendwakandaseguridad.repos.ContactoEmergenciaRepository;
import org.example.backendwakandaseguridad.service.AlertaService;
import org.example.backendwakandaseguridad.service.EmergenciaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
class BackendWakandaSeguridad {

	public static void main(String[] args) {
		SpringApplication.run(BackendWakandaSeguridad.class, args);
	}

	@Bean
	CommandLineRunner run(AlertaRepository alertaRepository, ContactoEmergenciaRepository contactoEmergenciaRepository, AlertaService alertaService, EmergenciaService emergenciaService) {
		return args -> {
			// Crear alertas iniciales
			Alerta alerta1 = new Alerta();
			alerta1.setTipo("Incendio");
			alerta1.setDescripcion("Incendio en el distrito central de Wakanda");
			alerta1.setFechaHora(LocalDateTime.now().minusHours(2));
			alerta1.setEstado(EstadoAlerta.ACTIVA);

			Alerta alerta2 = new Alerta();
			alerta2.setTipo("Desastre Natural");
			alerta2.setDescripcion("Inundación en la zona norte de Wakanda");
			alerta2.setFechaHora(LocalDateTime.now().minusDays(1));
			alerta2.setEstado(EstadoAlerta.RESUELTA);

			Alerta alerta3 = new Alerta();
			alerta3.setTipo("Fuga de Gas");
			alerta3.setDescripcion("Fuga de gas detectada en la fábrica de procesamiento de vibranium");
			alerta3.setFechaHora(LocalDateTime.now().minusMinutes(30));
			alerta3.setEstado(EstadoAlerta.ACTIVA);

			// Guardar las alertas en la base de datos
			List<Alerta> alertas = alertaRepository.saveAll(List.of(alerta1, alerta2, alerta3));

			alertas.forEach(alerta -> System.out.println("Alerta creada: " + alerta.getTipo() + " - Estado: " + alerta.getEstado()));

			// Crear contactos de emergencia iniciales
			ContactoEmergencia contacto1 = new ContactoEmergencia();
			contacto1.setTipoServicio("Policía");
			contacto1.setNumeroTelefono("112");

			ContactoEmergencia contacto2 = new ContactoEmergencia();
			contacto2.setTipoServicio("Bomberos");
			contacto2.setNumeroTelefono("113");

			ContactoEmergencia contacto3 = new ContactoEmergencia();
			contacto3.setTipoServicio("Ambulancia");
			contacto3.setNumeroTelefono("114");

			// Guardar los contactos de emergencia en la base de datos
			List<ContactoEmergencia> contactos = contactoEmergenciaRepository.saveAll(List.of(contacto1, contacto2, contacto3));

			contactos.forEach(contacto -> System.out.println("Contacto de emergencia creado: " + contacto.getTipoServicio() + " - Teléfono: " + contacto.getNumeroTelefono()));

			// Lógica de negocio adicional (ejemplos)
			System.out.println("[INFO] Listando alertas activas...");
			List<AlertaDTO> alertasActivas = alertaService.listarAlertas().stream()
					.filter(alerta -> alerta.getEstado().equals("ACTIVA"))
					.toList();
			alertasActivas.forEach(alerta -> System.out.println("Alerta activa: " + alerta.getTipo() + " - " + alerta.getDescripcion()));

			System.out.println("[INFO] Listando contactos de emergencia...");
			List<ContactoEmergenciaDTO> contactosEmergencia = emergenciaService.listarContactos();
			contactosEmergencia.forEach(contacto -> System.out.println("Contacto de emergencia: " + contacto.getTipoServicio() + " - Teléfono: " + contacto.getNumeroTelefono()));
		};
	}
}
