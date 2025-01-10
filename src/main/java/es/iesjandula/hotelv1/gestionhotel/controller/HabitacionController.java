package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.model.DTO.Habitacacion.HabitacionDTO;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Habitacacion.HabitacionRequestDTO;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.HabitacionUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.service.HabitacionService;
import es.iesjandula.hotelv1.gestionhotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    // Inyección de dependencias para el servicio de HabitacionService
    @Autowired
    private HabitacionService habitacionService;



    @Autowired
    private ReservaService reservaService;

   //Crear una habitación.
    @PostMapping
    public ResponseEntity<Habitacion> crearHabitacion(@RequestBody HabitacionRequestDTO habitacionRequestDTO) {
        try {
            //Convertimos el DTO en una habitación.

            Habitacion habitacion = new Habitacion();
            habitacion.setNumero(habitacionRequestDTO.getNumero());
            habitacion.setTipo(habitacionRequestDTO.getTipo());
            habitacion.setPrecio(habitacionRequestDTO.getPrecio());
            habitacion.setCapacidad(habitacionRequestDTO.getCapacidad());
            habitacion.setEstado(habitacionRequestDTO.getEstado());

            // Llamamos al servicio para crear la habitación, pasando el hotelId
            Habitacion newHabitacion = habitacionService.crearHabitacion(habitacion, habitacion.getNumero(), habitacionRequestDTO.getHotelId());

            // Si la habitación se crea correctamente, devolvemos un ResponseEntity con el estado CREATED
            return new ResponseEntity<>(newHabitacion, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //Buscar habitación por Id.
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtenerHabitacionPorId(@PathVariable Long id) {

        Optional<Habitacion> habitacion= habitacionService.obtenerHabitacionPorId(id);

        if(habitacion.isPresent()) {
            return new ResponseEntity<>(habitacion.get(), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //Listar todas las habitaciones.
    @GetMapping
    public ResponseEntity<List<Habitacion>> obtenerHabitaciones() {
        List<Habitacion> habitacion =habitacionService.obtenerListadoHabitaciones();
        return ResponseEntity.ok().body(habitacion);
    }


    // Actualizar una habitación
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacion(
            @PathVariable Long id,
            @RequestBody HabitacionUpdateDTO dto) {
        try {
            // Llamamos al servicio para actualizar la habitación, pasando el dto
            Habitacion habitacionActualizada = habitacionService.actualizarHabitacion(id, dto);

            // Devolvemos la habitación actualizada con un estado 200 OK
            return new ResponseEntity<>(habitacionActualizada, HttpStatus.OK);

        } catch (Exception e) {
            // Si ocurre un error, devolvemos un estado BAD_REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar una habitación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        try {
            // Llamamos al servicio para eliminar la habitación
            habitacionService.eliminarHabitacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 - No Content
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 - Not Found
        }
    }



    // Obtener habitaciones disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Habitacion>> obtenerHabitacionesDisponibles() {
        List<Habitacion> habitacionesDisponibles = habitacionService.obtenerHabitacionesDisponibles();

        if (habitacionesDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve HTTP 204 si no hay habitaciones disponibles
        }

        return ResponseEntity.ok(habitacionesDisponibles); // Devuelve HTTP 200 con la lista de habitaciones disponibles
    }

    // Obtener habitaciones ocupadas
    @GetMapping("/ocupadas")
    public ResponseEntity<List<Habitacion>> obtenerHabitacionesOcupadas() {
        List<Habitacion> habitacionesOcupadas = habitacionService.obtenerHabitacionesOcupadas();

        if (habitacionesOcupadas.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve HTTP 204 si no hay habitaciones ocupadas
        }

        return ResponseEntity.ok(habitacionesOcupadas); // Devuelve HTTP 200 con la lista de habitaciones ocupadas
    }



    @PutMapping("/ponerDisponibles")
    public String ponerTodasLasHabitacionesDisponibles() {
        habitacionService.ponerTodasLasHabitacionesDisponibles();
        return "Todas las habitaciones han sido actualizadas a DISPONIBLE";
    }




    /**
     * Endpoint para obtener las habitaciones ocupadas en una fecha concreta
     *
     * @param fecha Fecha a consultar (formato: YYYY-MM-DD)
     * @return Lista de habitaciones ocupadas
     */
    @GetMapping("/fechaEspecifica")
    public List<HabitacionDTO> obtenerHabitacionesOcupadas(@RequestParam("fecha") String fecha) {
        LocalDate fechaConsulta = LocalDate.parse(fecha);
        List<HabitacionDTO> habitacionesOcupadas = habitacionService.obtenerHabitacionesOcupadas(fechaConsulta);
        System.out.println("Habitaciones ocupadas para la fecha " + fechaConsulta + ": " + habitacionesOcupadas);
        return habitacionesOcupadas;
    }




    /**
     * Endpoint para obtener habitaciones disponibles por número de personas
     * @param numeroPersonas Número de personas a considerar
     * @return Lista de habitaciones disponibles
     */
    @GetMapping("/disponibles/por-personas")
    public ResponseEntity<List<Habitacion>> obtenerHabitacionesDisponiblesPorPersonas(@RequestParam int numeroPersonas) {
        List<Habitacion> habitacionesDisponibles = habitacionService.obtenerHabitacionesDisponiblesPorPersonas(numeroPersonas);
        return ResponseEntity.ok(habitacionesDisponibles);
    }
}
