package org.example.backendwakandaseguridad.repos;


import org.example.backendwakandaseguridad.domain.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si los necesitas
}
