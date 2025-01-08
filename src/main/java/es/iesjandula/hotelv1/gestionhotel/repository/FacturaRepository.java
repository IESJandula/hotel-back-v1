package es.iesjandula.hotelv1.gestionhotel.repository;

import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Puedes añadir consultas personalizadas aquí si es necesario.
   // Factura findByReserva(Reserva reserva);

    /*//metodo para obtener todas las facturas
    List<Factura> findAll();

    // Método para encontrar facturas por cliente
    List<Factura> findByClienteId(Long clienteId);*/
}
