// Paquete
package es.iesjandula.hotelv1.gestionhotel.controller;


// Importaciones
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.MetodoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Pago;
import es.iesjandula.hotelv1.gestionhotel.repository.PagoRepository;
import es.iesjandula.hotelv1.gestionhotel.service.PagoService;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Pagos.DatosDePago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * Definición del Controller PagoService.
 */
@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * Endpoint para crear un pago asociado a una reserva.
     *
     * @param reservaId   ID de la reserva.
     * @param datosDePago Datos necesarios para el pago.
     * @return Respuesta indicando el éxito o fallo de la operación.
     */
    @PostMapping("/{reservaId}")
    public ResponseEntity<String> crearPago(@PathVariable Long reservaId, @RequestBody DatosDePago datosDePago) {
        try {
            // Llamar al servicio para crear el pago
            pagoService.crearPago(reservaId, datosDePago);
            return ResponseEntity.ok("Pago realizado exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en la solicitud: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el pago: " + e.getMessage());
        }
    }

}
