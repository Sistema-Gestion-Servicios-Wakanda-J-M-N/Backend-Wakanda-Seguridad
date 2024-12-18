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

@SpringBootApplication
@EnableDiscoveryClient
public class BackendWakandaSeguridadApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendWakandaSeguridadApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AlertaRepository alertaRepository,
						  ContactoEmergenciaRepository contactoEmergenciaRepository,
						  AlertaService alertaService,
						  EmergenciaService emergenciaService) {
		return args -> {
			Random random = new Random();

			// 🔥 Crear contactos de emergencia iniciales
			List<ContactoEmergencia> contactosIniciales = List.of(
					new ContactoEmergencia("Policía", "112"),
					new ContactoEmergencia("Bomberos", "113"),
					new ContactoEmergencia("Ambulancia", "114"),
					new ContactoEmergencia("Rescate", "115")
			);

			List<ContactoEmergencia> contactos = contactoEmergenciaRepository.saveAll(contactosIniciales);
			System.out.println("\n[INFO] Listando contactos de emergencia al inicio...");
			contactos.forEach(contacto ->
					System.out.println("Contacto de emergencia: " + contacto.getTipoServicio() + " - Teléfono: " + contacto.getNumeroTelefono())
			);

			// 🔥 Crear alertas iniciales
			List<Alerta> alertasIniciales = List.of(
					new Alerta("Incendio", "Incendio en el distrito central de Wakanda", LocalDateTime.now().minusHours(2), EstadoAlerta.ACTIVA),
					new Alerta("Desastre Natural", "Inundación en la zona norte de Wakanda", LocalDateTime.now().minusDays(1), EstadoAlerta.RESUELTA),
					new Alerta("Fuga de Gas", "Fuga de gas detectada en la fábrica de procesamiento de vibranium", LocalDateTime.now().minusMinutes(30), EstadoAlerta.ACTIVA),
					new Alerta("Corte de Energía", "Corte de energía en el distrito industrial", LocalDateTime.now().minusMinutes(45), EstadoAlerta.ACTIVA),
					new Alerta("Accidente de Tránsito", "Accidente de tráfico en la autopista principal", LocalDateTime.now().minusMinutes(10), EstadoAlerta.ACTIVA),
					new Alerta("Explosión", "Explosión en la planta de vibranium", LocalDateTime.now().minusMinutes(5), EstadoAlerta.ACTIVA)
			);

			List<Alerta> alertas = alertaRepository.saveAll(alertasIniciales);
			alertas.forEach(alerta ->
					System.out.println("Alerta creada: " + alerta.getTipo() + " - Estado: " + alerta.getEstado())
			);

			// 🔥 Definir el scheduler para mostrar alertas dinámicas de 3 en 3
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(() -> {
				System.out.println("\n[INFO] Actualización dinámica de alertas activas");

				String[] ubicaciones = {"Distrito Central", "Zona Norte", "Zona Sur", "Área Industrial", "Calle Principal", "Avenida Principal"};
				EstadoAlerta[] estadosPosibles = EstadoAlerta.values();

				for (int i = 0; i < 3; i++) {
					// Seleccionar una alerta aleatoria de las 6
					Alerta alertaSeleccionada = alertas.get(random.nextInt(alertas.size()));

					// Generar ubicación y estado aleatorio para la alerta
					String nuevaUbicacion = ubicaciones[random.nextInt(ubicaciones.length)];
					EstadoAlerta nuevoEstado = estadosPosibles[random.nextInt(estadosPosibles.length)];

					// Crear una nueva alerta con estos valores
					Alerta nuevaAlerta = new Alerta(
							alertaSeleccionada.getTipo(),
							alertaSeleccionada.getTipo() + " en " + nuevaUbicacion,
							LocalDateTime.now(),
							nuevoEstado
					);

					// Guardar la nueva alerta en la base de datos
					Alerta alertaGuardada = alertaRepository.save(nuevaAlerta);
					System.out.println("[INFO] Nueva alerta activa creada: " + alertaGuardada.getTipo() + " - Descripción: " + alertaGuardada.getDescripcion() + " - Estado: " + alertaGuardada.getEstado());
				}

				System.out.println("\n[INFO] Listando todas las alertas activas...");
				List<AlertaDTO> alertasActivas = alertaService.listarAlertas().stream()
						.filter(alerta -> alerta.getEstado().equals("ACTIVA"))
						.toList();

				alertasActivas.forEach(alerta ->
						System.out.println("Alerta activa: " + alerta.getTipo() + " - " + alerta.getDescripcion() + " - Estado: " + alerta.getEstado())
				);

			}, 0, 30, TimeUnit.SECONDS);
		};
	}
}
