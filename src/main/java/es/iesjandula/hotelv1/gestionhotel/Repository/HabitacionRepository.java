package es.iesjandula.hotelv1.gestionhotel.Repository;

import es.iesjandula.hotelv1.gestionhotel.Model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
}
