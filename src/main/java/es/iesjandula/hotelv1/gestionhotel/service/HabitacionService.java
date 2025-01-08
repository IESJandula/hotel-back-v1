//Paquete.
package es.iesjandula.hotelv1.gestionhotel.service;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.exception.Habitacion.HabitacionNoEncontradaException;
import es.iesjandula.hotelv1.gestionhotel.exception.Hotel.HotelNoEncontradoException;
import es.iesjandula.hotelv1.gestionhotel.exception.Hotel.HotelYaExisteException;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Habitacacion.HabitacionDTO;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.HabitacionUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoReserva;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.HabitacionRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.HotelRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitacionService {
    @Autowired
    private HabitacionRepository habitacionRepository; //Inyección de dependencias.


    @Autowired
    private HotelRepository hotelRepository; //Inyección de dependencias.


    @Autowired
    private ReservaRepository reservaRepository; //Inyección de dependencia.




    //Creamos una habitación y asociarla a un hotel.
    @Transactional
    public Habitacion crearHabitacion(Habitacion habitacion, String numero, Long hotelId)  {
        //Verificamos si la habitación ya existe por el número.
        Optional<Habitacion> newHabitacion = habitacionRepository.findByNumero(numero);
        if(newHabitacion.isPresent()){
            throw new HotelYaExisteException("La Habitación ya existe");
        }


        //Buscar el hotel por su ID.
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new HotelNoEncontradoException("Hotel no encontrado"));

        //Asignamos el hotel a la habitación.
        habitacion.setHotelHabitacion(hotel);


        //Si no existe, guardamos la nueva habitación.
        return habitacionRepository.save(habitacion);
    }



    // Buscar una habitación por ID
    public Optional<Habitacion> obtenerHabitacionPorId(Long id) {
        // Usamos el repositorio para buscar la habitación por su ID.
        return habitacionRepository.findById(id);
    }


    //Listar las habitaciones.
    public List<Habitacion> obtenerListadoHabitaciones() {
        return habitacionRepository.findAll();
    }

    @Transactional
    public Habitacion actualizarHabitacion(Long id, HabitacionUpdateDTO dto) {

        // Buscamos la habitación por ID
        Habitacion habitacionExistente = habitacionRepository.findById(id)
                .orElseThrow(() -> new HabitacionNoEncontradaException("Habitación no encontrada"));

        // Actualizamos los valores con el DTO
        if (dto.getNumero() != null) habitacionExistente.setNumero(dto.getNumero());
        if (dto.getTipo() != null) habitacionExistente.setTipo(dto.getTipo());
        if (dto.getPrecio() != null) habitacionExistente.setPrecio(dto.getPrecio());
        if (dto.getCapacidad() != null) habitacionExistente.setCapacidad(dto.getCapacidad());
        if (dto.getEstado() != null) habitacionExistente.setEstado(dto.getEstado());

        // Asignamos el hotel a la habitación utilizando el hotelId del DTO
        if (dto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(dto.getHotelId())
                    .orElseThrow(() -> new HotelNoEncontradoException("Hotel no encontrado"));
            habitacionExistente.setHotelHabitacion(hotel);
        }

        // Guardamos los cambios
        return habitacionRepository.save(habitacionExistente);
    }


    //Eliminamos la habitación.
    @Transactional
    public void eliminarHabitacion(Long id){
        habitacionRepository.deleteById(id);
    }


    //Obtener habitaciones disponibles actuales.
    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return habitacionRepository.findAll()
                .stream()
                .filter(habitacion -> habitacion.getEstado() == EstadoHabitacion.DISPONIBLE)
                .collect(Collectors.toList());
    }


    //Obtener habitaciones ocupadas actuales.
    public List<Habitacion> obtenerHabitacionesOcupadas() {
        return habitacionRepository.findAll()
                .stream()
                .filter(habitacion -> habitacion.getEstado() == EstadoHabitacion.OCUPADA)
                .collect(Collectors.toList());
    }




    /**
     * Método para actualizar todas las habitaciones a estado DISPONIBLE.
     */
    @Transactional
    public void ponerTodasLasHabitacionesDisponibles() {
        List<Habitacion> habitaciones = habitacionRepository.findAll();
        for (Habitacion habitacion : habitaciones) {
            habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
        }
        habitacionRepository.saveAll(habitaciones);
    }




    /**
     * Obtener habitaciones ocupadas en una fecha específica
     *
     * @param fecha Fecha a consultar
     * @return Lista de habitaciones ocupadas en forma de DTO
     */
    public List<HabitacionDTO> obtenerHabitacionesOcupadas(LocalDate fecha) {
        // Obtener todas las habitaciones
        List<Habitacion> habitaciones = habitacionRepository.findAll();

        // Filtrar las habitaciones ocupadas
        List<HabitacionDTO> habitacionesOcupadas = new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {
            for (Reserva reserva : habitacion.getReservasHabitaciones()) {
                // Verificar si la reserva cubre la fecha solicitada
                if ((reserva.getFechaInicio().isBefore(fecha) || reserva.getFechaInicio().isEqual(fecha)) &&
                        (reserva.getFechaFin().isAfter(fecha) || reserva.getFechaFin().isEqual(fecha)) &&
                        reserva.getEstadoReserva().equals(EstadoReserva.PENDIENTE)) {
                    // Si está ocupada, agregamos la habitación al resultado
                    HabitacionDTO dto = new HabitacionDTO(
                            habitacion.getNumero(),
                            habitacion.getTipo(),
                            habitacion.getEstado()
                    );
                    habitacionesOcupadas.add(dto);
                    break;  // No necesitamos seguir verificando más reservas para esta habitación
                }
            }
        }

        return habitacionesOcupadas;
    }




    /*// Método para crear una nueva habitación
    public Habitacion crearHabitacion(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    // Método para obtener todas las habitaciones
    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return habitacionRepository.findAll();
    }

    // Método para obtener una habitación por su ID
    public Habitacion obtenerHabitacion(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada"));
    }

    // Método para obtener habitaciones disponibles entre dos fechas
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        return habitacionRepository.findHabitacionesDisponibles(fechaInicio, fechaFin);
    }

    // Método para obtener habitaciones ocupadas entre dos fechas
    public List<Habitacion> obtenerHabitacionesOcupadas(LocalDate fechaInicio, LocalDate fechaFin) {
        return habitacionRepository.findHabitacionesOcupadas(fechaInicio, fechaFin);
    }

    // Clase interna para manejar la excepción de recurso no encontrado
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }*/
}