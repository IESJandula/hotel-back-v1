//Paquete.
package es.iesjandula.hotelv1.gestionhotel.controller;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.exception.Reserva.ResevaNoEncontradaException;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Reserva.ReservaDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Definión del controlador ReservaController
 */
@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    // Método para crear una reserva
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            // Llamar al servicio para crear la reserva
            Reserva reserva = reservaService.crearReserva(
                    reservaDTO.getHotelReserva(),
                    reservaDTO.getClienteId(),
                    reservaDTO.getHabitacionIds(),
                    reservaDTO.getFechaInicio(),
                    reservaDTO.getFechaFin()
            );
            // Si la reserva se crea correctamente, retornamos la reserva con estado 201 (CREATED)
            return new ResponseEntity<>(reserva, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            // Si ocurre algún error, retornamos un error con estado 400 (BAD_REQUEST)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Listar las reservas.
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerReservas() {
        return ResponseEntity.ok(reservaService.obtenerListadoReservas());
    }

    //Obtener la reserva por ID.
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .map(reserva -> new ResponseEntity<>(reserva, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    //Controlador para cancelar la reserva.
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResevaNoEncontradaException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // 409 Conflict
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
        }
    }

}
