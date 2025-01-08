//Paquete.
package es.iesjandula.hotelv1.gestionhotel.repository;


//Importaciones
import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByEmail(String email);
}
