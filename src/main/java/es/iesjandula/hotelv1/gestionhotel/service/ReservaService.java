package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.Enum.EstadoReserva;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.FacturaRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.HabitacionRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteService clienteService;

    // Método para generar una nueva reserva
    public Reserva crearReserva(Long clienteId, int numeroHabitaciones, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener cliente
        Cliente cliente = clienteService.obtenerCliente(clienteId);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clienteId);
        }

        // Verificar disponibilidad de habitaciones para todas las fechas de la reserva
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            List<Habitacion> habitacionesDisponibles = habitacionRepository.findHabitacionesDisponibles(fechaInicio, fechaFin);
            if (habitacionesDisponibles.size() < numeroHabitaciones) {
                throw new RuntimeException("No hay suficientes habitaciones disponibles para las fechas seleccionadas.");
            }
        }

        // Obtener habitaciones disponibles para las fechas seleccionadas
        List<Habitacion> habitacionesSeleccionadas = habitacionRepository.findHabitacionesDisponibles(fechaInicio, fechaFin);
        if (habitacionesSeleccionadas.size() < numeroHabitaciones) {
            throw new RuntimeException("No hay suficientes habitaciones disponibles para la reserva.");
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setHabitaciones(habitacionesSeleccionadas.subList(0, numeroHabitaciones));

        // Asignar estado y precio por noche si no están configurados
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);  // Estado predeterminado
        reserva.setPrecioPorNoche(100.0);  // Precio predeterminado, puedes modificar según tu lógica

        // Guardar la reserva
        return reservaRepository.save(reserva);
    }

    // Método para obtener una reserva por ID
    public Reserva obtenerReservaPorId(long idReserva) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        return reservaOpt.orElseThrow(() -> new RuntimeException("Reserva no encontrada con el ID: " + idReserva));
    }

    // Método para actualizar una reserva
    public Reserva actualizarReserva(Long id, Reserva nuevosDatos) {
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setCliente(nuevosDatos.getCliente());
        reserva.setFechaInicio(nuevosDatos.getFechaInicio());
        reserva.setFechaFin(nuevosDatos.getFechaFin());
        return reservaRepository.save(reserva);
    }

    // Método para eliminar una reserva
    public void eliminarReserva(Long id) {
        Reserva reserva = obtenerReservaPorId(id);
        reservaRepository.delete(reserva);
    }

    // Método para obtener reservas de un cliente específico
    public List<Reserva> obtenerReservasPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    // Método para generar factura a partir de una reserva
    public Factura generarFactura(Long idReserva) {
        Reserva reserva = obtenerReservaPorId(idReserva);

        // Calcular el total de la factura
        double totalFactura = reserva.getTotal();

        // Crear una nueva factura
        Factura factura = new Factura();
        factura.setReserva(reserva);  // Asocia la factura con la reserva
        factura.setTotal(totalFactura);

        // Guardar la factura
        return facturaRepository.save(factura);
    }
}
