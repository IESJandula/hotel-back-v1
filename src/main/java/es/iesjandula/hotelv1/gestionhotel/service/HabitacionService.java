package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitacionService {
    @Autowired
    private HabitacionRepository habitacionRepository;

    // Método para crear una nueva habitación
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
    }
}