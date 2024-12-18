package org.example.backendwakandaseguridad;

import org.example.backendwakandaseguridad.domain.Alerta;
import org.example.backendwakandaseguridad.domain.ContactoEmergencia;
import org.example.backendwakandaseguridad.domain.EstadoAlerta;
import org.example.backendwakandaseguridad.model.AlertaDTO;
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
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "org.example.backendwakandaseguridad")
@EnableDiscoveryClient
public class BackendWakandaSeguridadApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendWakandaSeguridadApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
			AlertaRepository alertaRepository,
			ContactoEmergenciaRepository contactoRepository,
			AlertaService alertaService,
			EmergenciaService emergenciaService) {

		return args -> {
			Random random = new Random();

			// Crear contactos de emergencia
			List<ContactoEmergencia> contactos = List.of(
					new ContactoEmergencia("Policía", "112"),
					new ContactoEmergencia("Bomberos", "113"),
					new ContactoEmergencia("Ambulancia", "114"),
					new ContactoEmergencia("Rescate", "115")
			);

			contactoRepository.saveAll(contactos);
			System.out.println("\n[INFO] Listando contactos de emergencia al inicio...");
			contactoRepository.findAll().forEach(contacto ->
					System.out.println("Contacto de emergencia: " + contacto.getTipoServicio() + " - Teléfono: " + contacto.getNumeroTelefono())
			);

			// Crear alertas iniciales
			List<Alerta> alertas = List.of(
					new Alerta("Incendio", "Incendio en la zona industrial", LocalDateTime.now(), EstadoAlerta.ACTIVA),
					new Alerta("Desastre Natural", "Inundación en el sector norte", LocalDateTime.now(), EstadoAlerta.RESUELTA),
					new Alerta("Fuga de Gas", "Fuga de gas en la fábrica central", LocalDateTime.now(), EstadoAlerta.ACTIVA),
					new Alerta("Corte de Energía", "Corte de energía en el distrito sur", LocalDateTime.now(), EstadoAlerta.ACTIVA),
					new Alerta("Accidente de Tránsito", "Accidente en la avenida principal", LocalDateTime.now(), EstadoAlerta.ACTIVA),
					new Alerta("Explosión", "Explosión reportada en planta química", LocalDateTime.now(), EstadoAlerta.ACTIVA)
			);

			alertaRepository.saveAll(alertas);
			alertas.forEach(alerta ->
					System.out.println("Alerta creada: " + alerta.getTipo() + " - Estado: " + alerta.getEstado())
			);

			// Tareas programadas
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(() -> {
				System.out.println("\n[INFO] Actualización dinámica de alertas activas");

				String[] ubicaciones = {"Zona Sur", "Distrito Central", "Sector Norte", "Calle Principal", "Avenida Principal"};
				EstadoAlerta[] estados = EstadoAlerta.values();

				for (int i = 0; i < 3; i++) {
					Alerta nuevaAlerta = new Alerta(
							alertas.get(random.nextInt(alertas.size())).getTipo(),
							alertas.get(random.nextInt(alertas.size())).getTipo() + " en " + ubicaciones[random.nextInt(ubicaciones.length)],
							LocalDateTime.now(),
							estados[random.nextInt(estados.length)]
					);
					alertaRepository.save(nuevaAlerta);
					System.out.println("Nueva alerta activa creada: " + nuevaAlerta.getTipo() + " - Descripción: " + nuevaAlerta.getDescripcion() + " - Estado: " + nuevaAlerta.getEstado());
				}

				System.out.println("\n[INFO] Listando todas las alertas activas...");
				alertaService.listarAlertas().stream()
						.filter(alertaDTO -> alertaDTO.getEstado().equals("ACTIVA"))
						.forEach(alertaDTO ->
								System.out.println("Alerta activa: " + alertaDTO.getTipo() + " - Descripción: " + alertaDTO.getDescripcion())
						);
			}, 0, 30, TimeUnit.SECONDS);
		};
	}
}
