package org.example.backendwakandaseguridad.repos;


import org.example.backendwakandaseguridad.domain.ContactoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si los necesitas
}
