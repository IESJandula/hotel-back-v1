package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Metodos personalizados.
   Optional<Cliente> findByEmail(String email); //Buscar cliente por email.
}
