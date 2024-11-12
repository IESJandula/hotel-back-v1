package es.iesjandula.hotelv1.gestionhotel.Repository;

import es.iesjandula.hotelv1.gestionhotel.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Metodos
}
