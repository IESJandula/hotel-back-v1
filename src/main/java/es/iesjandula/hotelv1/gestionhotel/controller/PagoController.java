// Paquete
package es.iesjandula.hotelv1.gestionhotel.controller;

// Importaciones
import es.iesjandula.hotelv1.gestionhotel.Enum.EstadoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Pago;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import es.iesjandula.hotelv1.gestionhotel.service.PagoService;

/**
 * Definición del Controller PagoService.
 */
@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;


    // Crear un nuevo pago.
    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.crearPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }


    //Obtener un pago por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPago(@PathVariable Long id) {
        try {
            Pago pago = pagoService.obtenerPago(id);
            return new ResponseEntity<>(pago, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Actualizar el estado de un pago.
    @PutMapping("/{id}/estado")
    public ResponseEntity<Pago> actualizarEstadoPago(@PathVariable Long id, @RequestParam EstadoPago nuevoEstado) {
        try {
            Pago pagoActualizado = pagoService.actualizarEstadoPago(id, nuevoEstado);
            return new ResponseEntity<>(pagoActualizado, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar un pago por su ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Opcional para obtener todos los pagos.
    @GetMapping
    public ResponseEntity<List<Pago>> obtenerTodosLosPagos() {
        List<Pago> pagos = pagoService.obtenerTodosLosPagos();
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }
}
