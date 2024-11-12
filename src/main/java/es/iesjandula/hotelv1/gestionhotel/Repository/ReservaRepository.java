package es.iesjandula.hotelv1.gestionhotel.Repository;

import es.iesjandula.hotelv1.gestionhotel.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
