//Paquete.
package es.iesjandula.hotelv1.gestionhotel.service;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.exception.Cliente.ClienteNoEncontradoException;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoReserva;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.HabitacionRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.HotelRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.iesjandula.hotelv1.gestionhotel.exception.Reserva.ResevaNoEncontradaException;
import es.iesjandula.hotelv1.gestionhotel.exception.Hotel.HotelNoEncontradoException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository; //Inyección de dependencias.

    @Autowired
    private HabitacionRepository habitacionRepository; //Inyección de dependencias.

    @Autowired
    private HotelRepository hotelRepository;


    @Autowired
    private ClienteRepository clienteRepository;


    @Transactional
    public Reserva crearReserva(Long hotelId, Long clienteId, List<Long> habitacionIds, LocalDate fechaInicio, LocalDate fechaFin) {

        if (hotelId == null) {
            throw new IllegalArgumentException("El ID del hotel no puede ser nulo");
        }
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        if (habitacionIds == null || habitacionIds.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una habitación");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }


        // Verificar si el hotel existe
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        // Verificar si el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Verificar si las habitaciones existen
        List<Habitacion> habitaciones = habitacionRepository.findAllById(habitacionIds);
        if (habitaciones.isEmpty()) {
            throw new RuntimeException("Habitaciones no encontradas");
        }

        // Verificar si las habitaciones están disponibles
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstado() != EstadoHabitacion.DISPONIBLE) {
                throw new RuntimeException("Habitación " + habitacion.getNumero() + " no disponible");
            }
        }

        // Calcular el precio total
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias <= 0) {
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        double precioPorNoche = habitaciones.stream().mapToDouble(Habitacion::getPrecio).sum();
        double precioTotal = precioPorNoche * dias;

        // Crear la reserva
        Reserva reserva = new Reserva(fechaInicio, fechaFin, EstadoReserva.PENDIENTE, precioPorNoche);
        reserva.setClienteReserva(cliente);
        reserva.setHotelReserva(hotel);
        reserva.setReservasHabitaciones(habitaciones);

        // Guardar la reserva
        Reserva nuevaReserva = reservaRepository.save(reserva);

        // Actualizar el estado de las habitaciones a "Ocupada"
        habitaciones.forEach(h -> h.setEstado(EstadoHabitacion.OCUPADA));
        habitacionRepository.saveAll(habitaciones);

        return nuevaReserva;
    }



    public List<Reserva> obtenerListadoReservas(){
        return reservaRepository.findAll();
    }


    public Optional<Reserva> obtenerReservaPorId(Long id){
        return reservaRepository.findById(id);
    }




    @Transactional
    public void cancelarReserva(Long id) {
        // Buscar la reserva por ID
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResevaNoEncontradaException("Reserva con ID " + id + " no encontrada"));

        // Verificar si la reserva ya está cancelada
        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("La reserva ya está cancelada");
        }

        // Cambiar el estado de la reserva a CANCELADA
        reserva.setEstadoReserva(EstadoReserva.CANCELADA);

        // Liberar habitaciones asociadas a la reserva
        List<Habitacion> habitaciones = reserva.getReservasHabitaciones();
        if (habitaciones != null && !habitaciones.isEmpty()) {
            for (Habitacion habitacion : habitaciones) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
            }
            try {
                // Guardar los cambios en las habitaciones
                habitacionRepository.saveAll(habitaciones);
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar el estado de las habitaciones", e);
            }
        }

        try {
            // Guardar los cambios en la reserva
            reservaRepository.save(reserva);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la reserva", e);
        }
    }
}