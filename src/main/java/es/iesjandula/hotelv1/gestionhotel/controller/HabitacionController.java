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

    @Autowired
    private HabitacionService habitacionService;

    @PostMapping("/crearHabitacion")
    public ResponseEntity<Habitacion> crearHabitacion(@RequestBody Habitacion habitacion) {
        Habitacion nuevaHabitacion = habitacionService.crearHabitacion(habitacion);
        return ResponseEntity.ok(nuevaHabitacion);
    }
    @GetMapping("/obtenerTodasLasHabitaciones")
    public ResponseEntity<List<Habitacion>> obtenerTodasLasHabitaciones() {
        List<Habitacion> habitaciones = habitacionService.obtenerTodasLasHabitaciones();
        return ResponseEntity.ok(habitaciones);
    }
    @GetMapping("/{id}")
    public Habitacion obtenerHabitacion(@PathVariable Long id) {
        return habitacionService.obtenerHabitacion(id);
    }
    @GetMapping("/disponibles")
    public List<Habitacion> obtenerHabitacionesDisponibles(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        return habitacionService.obtenerHabitacionesDisponibles(inicio, fin);
    }
    @GetMapping("/ocupadas")
    public List<Habitacion> obtenerHabitacionesOcupadas(
            @RequestParam("fechaInicio") String fechaInicioStr,
            @RequestParam("fechaFin") String fechaFinStr) {
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);

        return habitacionService.obtenerHabitacionesOcupadas(fechaInicio, fechaFin);
    }
}
