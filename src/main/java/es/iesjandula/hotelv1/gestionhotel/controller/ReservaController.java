package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.DTO.ReservaDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Endpoint para crear una nueva reserva
    @PostMapping("/reservar")
    public ResponseEntity<?> realizarReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            // Llamamos al servicio para crear la reserva utilizando los datos del DTO
            Reserva reserva = reservaService.crearReserva(
                    reservaDTO.getClienteId(),
                    reservaDTO.getNumeroHabitaciones(),
                    reservaDTO.getFechaInicio(),
                    reservaDTO.getFechaFin()
            );
            return new ResponseEntity<>(reserva, HttpStatus.CREATED); // Reserva creada correctamente
        } catch (Exception e) {
            // Manejo de excepciones más claro
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Errores en la creación de reserva
        }
    }


    // Endpoint para obtener una reserva específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable long id) {
        try {
            Reserva reserva = reservaService.obtenerReservaPorId(id);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Reserva no encontrada
        }
    }

    // Endpoint para obtener todas las reservas de un cliente específico
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorCliente(@PathVariable Long idCliente) {
        List<Reserva> reservas = reservaService.obtenerReservasPorCliente(idCliente);
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no hay reservas, devuelve un 204
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    // Endpoint para cancelar (eliminar) una reserva por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        try {
            reservaService.eliminarReserva(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 - Sin contenido
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Reserva no encontrada
        }
    }

    // Endpoint para actualizar una reserva
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva nuevosDatos) {
        try {
            Reserva reservaActualizada = reservaService.actualizarReserva(id, nuevosDatos);
            return new ResponseEntity<>(reservaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Reserva no encontrada
        }
    }

    // Endpoint para generar factura a partir de una reserva de un cliente
    @GetMapping("/factura/{idReserva}")
    public ResponseEntity<Factura> generarFactura(@PathVariable Long idReserva) {
        try {
            // Llama al servicio para generar la factura
            Factura factura = reservaService.generarFactura(idReserva);
            return new ResponseEntity<>(factura, HttpStatus.OK); // Devuelve la factura generada
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Si hay algún error, se devuelve un 400
        }
    }
}
