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

    //Metodo para obtener todas las habitaciones
    public Habitacion crearHabitacion(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }
    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return habitacionRepository.findAll(); // Llama al método del repositorio para obtener todas las habitaciones
    }
    public Habitacion obtenerHabitacion(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitacion no encontrada"));
    }
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        return habitacionRepository.findHabitacionesDisponibles(fechaInicio, fechaFin);
    }
}
