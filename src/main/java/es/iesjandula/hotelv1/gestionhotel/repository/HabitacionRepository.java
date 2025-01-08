package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(String numero);

    /*// Buscar habitaciones disponibles en un rango de fechas
    @Query("SELECT h FROM Habitacion h WHERE h.id NOT IN " +
            "(SELECT hab.id FROM Reserva r JOIN r.habitaciones hab " +
            "WHERE (r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio))")
    List<Habitacion> findHabitacionesDisponibles(@Param("fechaInicio") LocalDate fechaInicio,
                                                 @Param("fechaFin") LocalDate fechaFin);

    // Buscar habitaciones ocupadas en un rango de fechas
    @Query("SELECT h FROM Habitacion h JOIN h.reservas r " +
            "WHERE (r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio)")
    List<Habitacion> findHabitacionesOcupadas(@Param("fechaInicio") LocalDate fechaInicio,
                                              @Param("fechaFin") LocalDate fechaFin);*/


}
