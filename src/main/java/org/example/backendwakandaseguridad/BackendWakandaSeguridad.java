package org.example.backendwakandaseguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BackendWakandaSeguridad {

	public static void main(String[] args) {
		SpringApplication.run(BackendWakandaSeguridad.class, args);
	}

}
