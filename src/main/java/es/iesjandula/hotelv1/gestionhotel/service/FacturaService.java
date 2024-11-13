//Paquete.
package es.iesjandula.hotelv1.gestionhotel.service;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Servicio para manejar la lógica de negocio de las facturas.
 */
@Service
public class FacturaService {

    //Atributos
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ReservaService reservaService;

    /**
     * Genera una factura basada en la reserva con el ID proporcionado.
     * @param idReserva El ID de la reserva.
     * @return La factura generada.
     */
    public Factura generarFactura(Long idReserva) {
        // Obtener la reserva por ID
        Reserva reserva = reservaService.obtenerReservaPorId(idReserva);

        // Verificar si la reserva existe
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva no encontrada para el ID proporcionado.");
        }

        // Obtener el cliente asociado a la reserva
        Cliente cliente = reserva.getCliente();

        // Verificar si el cliente está asociado
        if (cliente == null) {
            throw new IllegalArgumentException("La reserva no tiene un cliente asociado.");
        }

        // Calcular el total (supongamos un cálculo simplificado por número de noches y precio por noche)
        long noches = java.time.temporal.ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        double total = noches * reserva.getPrecioPorNoche();

        // Crear la factura
        Factura factura = new Factura(reserva, cliente, total, LocalDate.now());

        // Guardar y retornar la factura
        return facturaRepository.save(factura);
    }
}
