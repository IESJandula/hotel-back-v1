//Paquete.
package es.iesjandula.hotelv1.gestionhotel.controller;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.DTO.ReservaDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
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
    private ReservaService reservaService; //Inyecciones de dependencias.


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

    // Método para obtener una reserva por ID
    @GetMapping("/{id}")
    public Reserva obtenerReserva(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id);
    }

    // Método para obtener todas las reservas de un cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Reserva> obtenerReservasPorCliente(@PathVariable Long clienteId) {
        return reservaService.obtenerReservasPorCliente(clienteId);
    }

    // Método para actualizar una reserva
    @PutMapping("/actualizar/{id}")
    public Reserva actualizarReserva(@PathVariable Long id, @RequestBody Reserva nuevosDatos) {
        return reservaService.actualizarReserva(id, nuevosDatos);
    }

    // Método para eliminar una reserva
    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    // Método para generar una factura a partir de una reserva
    @PostMapping("/generarFactura/{id}")
    public Factura generarFactura(@PathVariable Long id) {
        return reservaService.generarFactura(id);
    }
}
