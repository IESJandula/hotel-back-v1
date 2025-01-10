// Paquete
package es.iesjandula.hotelv1.gestionhotel.service;

// Importaciones
import es.iesjandula.hotelv1.gestionhotel.exception.Reserva.ReservaNotFoundException;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Pagos.DatosDePago;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoReserva;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.MetodoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Pago;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.HabitacionRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.PagoRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Servicio de Pago.
 */
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    /**
     * Crea un pago asociado a una reserva.
     *
     * @param reservaId ID de la reserva.
     * @param datosDePago Datos necesarios para procesar el pago.
     */
    @Transactional
    public void crearPago(Long reservaId, DatosDePago datosDePago) {
        try {
            // Paso 1: Buscar reserva...
            System.out.println("Paso 1: Buscar reserva...");
            Reserva reserva = reservaRepository.findById(reservaId)
                    .orElseThrow(() -> new ReservaNotFoundException("Reserva no encontrada"));
            System.out.println("Reserva encontrada: " + reserva);

            // Paso 2: Validar estado de reserva...
            System.out.println("Paso 2: Validar estado de reserva...");
            if (reserva.getEstadoReserva() != EstadoReserva.PENDIENTE) {
                throw new IllegalStateException("El estado no es pendiente, no se puede procesar el pago.");
            }

            // Paso 3: Verificar disponibilidad de habitaciones (solo RESERVADAS son válidas para el pago)
            System.out.println("Paso 3: Verificar disponibilidad de habitaciones...");
            for (Habitacion habitacion : reserva.getReservasHabitaciones()) {
                // Solo las habitaciones en estado RESERVADA pueden proceder al pago
                if (habitacion.getEstado() != EstadoHabitacion.OCUPADA) {
                    throw new IllegalStateException("Una o más habitaciones no están reservadas y no se puede procesar el pago.");
                }
            }

            // Paso 4: Calcular precio total...
            System.out.println("Paso 4: Calcular precio total...");
            long numeroDeNoches = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
            Double precioTotal = numeroDeNoches * reserva.getPrecioPorNoche();
            System.out.println("Precio total calculado: " + precioTotal);

            // Paso 5: Verificar monto...
            System.out.println("Paso 5: Verificar monto...");
            if (!datosDePago.getMonto().equals(precioTotal)) {
                throw new IllegalStateException("El monto del pago no coincide con el precio.");
            }

            // Paso 6: Simular el proceso de pago...
            System.out.println("Paso 6: Simular proceso de pago...");
            if (!simularPago(datosDePago)) {
                throw new IllegalStateException("El pago no fue válido.");
            }

            // Paso 7: Crear entidad Pago...
            System.out.println("Paso 7: Crear entidad Pago...");
            Pago pago = new Pago(
                    datosDePago.getMonto(),
                    new Date(),
                    datosDePago.getMetodoPago(),
                    EstadoPago.COMPLETADO,
                    reserva
            );
            pagoRepository.save(pago); // Persistir el pago
            pagoRepository.flush(); // Forzar escritura inmediata en la base de datos
            System.out.println("Pago guardado exitosamente en la base de datos: " + pago);

            // Paso 8: Cambiar estado de la reserva...
            System.out.println("Paso 8: Cambiar estado de la reserva...");
            reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
            reservaRepository.save(reserva);

            // Paso 9: Cambiar estado de las habitaciones...
            System.out.println("Paso 9: Cambiar estado de las habitaciones...");
            List<Habitacion> habitaciones = reserva.getReservasHabitaciones();
            habitaciones.forEach(habitacion -> habitacion.setEstado(EstadoHabitacion.OCUPADA));
            habitacionRepository.saveAll(habitaciones);

            // Paso 10: Enviar confirmación...
            System.out.println("Paso 10: Enviar confirmación...");
            enviarConfirmacionReserva(reserva.getClienteReserva());

        } catch (Exception e) {
            System.err.println("Error durante la creación del pago: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error durante la creación del pago.", e);
        }
    }




    /**
     * Simula el proceso de pago.
     *
     * @param datosDePago Datos necesarios para la simulación.
     * @return true si el pago es válido, false en caso contrario.
     */
    public boolean simularPago(DatosDePago datosDePago) {
        if (datosDePago.getMetodoPago() == MetodoPago.EFECTIVO) {
            System.out.println("Pago simulado con EFECTIVO.");
            return true; // Pago siempre válido en efectivo.
        } else if (datosDePago.getMetodoPago() == MetodoPago.TARJETA_CREDITO || datosDePago.getMetodoPago() == MetodoPago.TARJETA_DEBITO) {
            // Validar los detalles de la tarjeta.
            if (datosDePago.getNumeroTarjeta() != null && datosDePago.getCvv() != null) {
                System.out.println("Pago simulado con tarjeta exitoso.");
                return true;
            } else {
                System.out.println("Pago fallido: detalles de tarjeta incompletos.");
                return false;
            }
        } else if (datosDePago.getMetodoPago() == MetodoPago.TRANSFERENCIA) {
            System.out.println("Pago simulado con TRANSFERENCIA.");
            return true;
        } else if (datosDePago.getMetodoPago() == MetodoPago.PAYPAL) {
            System.out.println("Pago simulado con PAYPAL.");
            return true;
        }
        System.out.println("Método de pago no soportado.");
        return false;
    }

    /**
     * Simula el envío de un correo de confirmación.
     *
     * @param cliente Cliente al que se enviará la confirmación.
     */
    public void enviarConfirmacionReserva(Cliente cliente) {
        System.out.println("Correo de confirmación enviado a: " + cliente.getEmail());
    }
}
