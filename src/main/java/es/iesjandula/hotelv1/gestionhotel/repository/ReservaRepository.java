package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Este método busca todas las reservas asociadas a un cliente usando su id.
    List<Reserva> findByClienteId(Long clienteId);
}
