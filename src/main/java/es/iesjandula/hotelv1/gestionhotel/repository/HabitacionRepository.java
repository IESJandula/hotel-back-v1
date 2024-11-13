package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    @Query("SELECT h FROM Habitacion h WHERE h.id NOT IN (SELECT r.habitacion.id FROM Reserva r WHERE (r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio))")
    List<Habitacion> findHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT h FROM Habitacion h JOIN h.reservas r " + "WHERE (r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio)")
    List<Habitacion> findHabitacionesOcupadas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}