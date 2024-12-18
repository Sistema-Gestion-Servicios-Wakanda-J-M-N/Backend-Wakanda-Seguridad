package org.example.backendwakandaseguridad;

import org.example.backendwakandaseguridad.domain.Alerta;
import org.example.backendwakandaseguridad.domain.ContactoEmergencia;
import org.example.backendwakandaseguridad.domain.EstadoAlerta;
import org.example.backendwakandaseguridad.repos.AlertaRepository;
import org.example.backendwakandaseguridad.repos.ContactoEmergenciaRepository;
import org.example.backendwakandaseguridad.service.AlertaService;
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
	CommandLineRunner run(AlertaRepository alertaRepository,
						  ContactoEmergenciaRepository contactoRepository,
						  AlertaService alertaService) {
		return args -> {
			Random random = new Random();

			// Limpiar tablas
			alertaRepository.deleteAll();
			contactoRepository.deleteAll();

			// Crear contactos de emergencia únicos
			List<ContactoEmergencia> contactos = List.of(
					new ContactoEmergencia("Policía", "112"),
					new ContactoEmergencia("Bomberos", "113"),
					new ContactoEmergencia("Ambulancia", "114"),
					new ContactoEmergencia("Rescate", "115")
			);

			contactoRepository.saveAll(contactos);
			System.out.println("\n[INFO] Contactos de emergencia al inicio:");
			contactos.forEach(contacto ->
					System.out.println("Contacto: " + contacto.getTipoServicio() + " - Teléfono: " + contacto.getNumeroTelefono())
			);

			// Crear una alerta inicial
			List<Alerta> alertasIniciales = List.of(
					new Alerta("Incendio", "Incendio en la zona industrial", LocalDateTime.now(), EstadoAlerta.ACTIVA)
			);

			alertaRepository.saveAll(alertasIniciales);

			// Programar generación progresiva de alertas dinámicas
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(() -> {
				System.out.println("\n[INFO] Generando una nueva alerta dinámica...");

				String[] tiposDeAlerta = {"Incendio", "Desastre Natural", "Fuga de Gas", "Corte de Energía", "Accidente de Tránsito", "Explosión"};
				String[] ubicaciones = {"Zona Sur", "Distrito Central", "Sector Norte", "Calle Principal", "Avenida Principal"};
				EstadoAlerta[] estados = EstadoAlerta.values();

				// Generar una alerta aleatoria
				Alerta nuevaAlerta = new Alerta(
						tiposDeAlerta[random.nextInt(tiposDeAlerta.length)],
						"Evento en " + ubicaciones[random.nextInt(ubicaciones.length)],
						LocalDateTime.now(),
						estados[random.nextInt(estados.length)]
				);

				alertaRepository.save(nuevaAlerta);
				System.out.println("Nueva alerta: " + nuevaAlerta.getTipo() + " - " + nuevaAlerta.getDescripcion() + " - Estado: " + nuevaAlerta.getEstado());

				// Listar las alertas activas
				System.out.println("\n[INFO] Listando alertas activas:");
				alertaService.listarAlertas().stream()
						.filter(alerta -> alerta.getEstado().equals("ACTIVA"))
						.forEach(alerta ->
								System.out.println("Alerta: " + alerta.getTipo() + " - Descripción: " + alerta.getDescripcion())
						);

			}, 0, 15, TimeUnit.SECONDS); // Generar una alerta cada 15 segundos
		};
	}
}
