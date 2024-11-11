package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Metodos
}
