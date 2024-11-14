package es.iesjandula.hotelv1.gestionhotel.service;

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
import java.util.Optional;

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
    private ClienteService clienteService; // Asegúrate de tener el servicio de Cliente

    //metodo para generar una nueva reserva
    public Reserva crearReserva(Long clienteId, int numeroHabitaciones, LocalDate fechaInicio, LocalDate fechaFin) {
        Cliente cliente = clienteService.obtenerCliente(clienteId);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clienteId);
        }

        // Verificar disponibilidad de habitaciones para todas las fechas de la reserva
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            // PASAR AMBOS PARAMETROS: fechaInicio y fechaFin
            List<Habitacion> habitacionesDisponibles = habitacionRepository.findHabitacionesDisponibles(fechaInicio, fechaFin);
            if (habitacionesDisponibles.size() < numeroHabitaciones) {
                throw new RuntimeException("No hay suficientes habitaciones disponibles para las fechas seleccionadas.");
            }
        }

        // Crear la reserva y asignar las habitaciones
        List<Habitacion> habitacionesSeleccionadas = habitacionRepository.findHabitacionesDisponibles(fechaInicio, fechaFin);
        if (habitacionesSeleccionadas.size() < numeroHabitaciones) {
            throw new RuntimeException("No hay suficientes habitaciones disponibles para la reserva.");
        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setHabitaciones(habitacionesSeleccionadas.subList(0, numeroHabitaciones)); // Asignamos las habitaciones seleccionadas

        // Guardar la reserva en la base de datos
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
    public Factura generarFactura(Long idReserva) throws Exception {
        // Obtener la reserva por ID
        Reserva reserva = reservaRepository.findById(idReserva).orElseThrow(() -> new Exception("Reserva no encontrada"));

        // Verificar si la reserva tiene un cliente asignado
        if (reserva.getCliente() == null) {
            throw new RuntimeException("La reserva no tiene un cliente asignado.");
        }

        // Crear una nueva factura
        Factura factura = new Factura();
        factura.setReserva(reserva); // Asocia la reserva a la factura
        factura.setTotal(reserva.getTotal()); // Calcula el total de la factura a partir del total de la reserva
        factura.setFecha(LocalDate.now()); // Establece la fecha actual para la factura

        // Guardar la factura en la base de datos
        return facturaRepository.save(factura); // Guarda la factura y la devuelve
    }
}
