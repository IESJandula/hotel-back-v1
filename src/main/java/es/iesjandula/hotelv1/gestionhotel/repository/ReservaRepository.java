package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Buscar reservas por ID del cliente
    List<Reserva> findByClienteReserva_Id(Long clienteId);

    // Buscar reservas por ID de la habitación (usando la relación ManyToMany)
    List<Reserva> findByReservasHabitaciones_Id(Long habitacionId);

    // Buscar reservas por estado
    List<Reserva> findByEstadoReserva(String estadoReserva);

    // Buscar reservas por rango de fechas (Fecha de inicio y fecha de fin)
    List<Reserva> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Buscar reservas por hotel
    List<Reserva> findByHotelReserva_Id(Long hotelId);


    // Buscar reservas que incluyen una fecha específica en su rango
    List<Reserva> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate fechaInicio, LocalDate fechaFin);



}
