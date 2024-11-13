package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.service.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/habitacion")
public class HabitacionController {

    // Inyección de dependencias para el servicio de HabitacionService
    @Autowired
    private HabitacionService habitacionService;

    /**
     * Endpoint para crear una nueva habitación.
     * @param habitacion Objeto Habitacion enviado en la solicitud.
     * @return La habitación creada dentro de un ResponseEntity.
     */
    @PostMapping("/crearHabitacion")
    public ResponseEntity<Habitacion> crearHabitacion(@RequestBody Habitacion habitacion) {
        Habitacion nuevaHabitacion = habitacionService.crearHabitacion(habitacion);
        return ResponseEntity.ok(nuevaHabitacion);
    }

    /**
     * Endpoint para obtener todas las habitaciones.
     * @return Lista de todas las habitaciones en un ResponseEntity.
     */
    @GetMapping("/obtenerTodasLasHabitaciones")
    public ResponseEntity<List<Habitacion>> obtenerTodasLasHabitaciones() {
        List<Habitacion> habitaciones = habitacionService.obtenerTodasLasHabitaciones();
        return ResponseEntity.ok(habitaciones);
    }

    /**
     * Endpoint para obtener una habitación específica por su ID.
     * @param id Identificador de la habitación a buscar.
     * @return La habitación encontrada o una excepción si no existe.
     */
    @GetMapping("/{id}")
    public Habitacion obtenerHabitacion(@PathVariable Long id) {
        return habitacionService.obtenerHabitacion(id);
    }

    /**
     * Endpoint para obtener habitaciones disponibles entre dos fechas específicas.
     * @param fechaInicio Fecha de inicio del rango en formato String (yyyy-MM-dd).
     * @param fechaFin Fecha de fin del rango en formato String (yyyy-MM-dd).
     * @return Lista de habitaciones disponibles en el rango de fechas especificado.
     */
    @GetMapping("/disponibles")
    public List<Habitacion> obtenerHabitacionesDisponibles(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        return habitacionService.obtenerHabitacionesDisponibles(inicio, fin);
    }

    /**
     * Endpoint para obtener habitaciones ocupadas entre dos fechas específicas.
     * @param fechaInicioStr Fecha de inicio del rango en formato String (yyyy-MM-dd).
     * @param fechaFinStr Fecha de fin del rango en formato String (yyyy-MM-dd).
     * @return Lista de habitaciones ocupadas en el rango de fechas especificado.
     */
    @GetMapping("/ocupadas")
    public List<Habitacion> obtenerHabitacionesOcupadas(
            @RequestParam("fechaInicio") String fechaInicioStr,
            @RequestParam("fechaFin") String fechaFinStr) {
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);

        return habitacionService.obtenerHabitacionesOcupadas(fechaInicio, fechaFin);
    }
}
