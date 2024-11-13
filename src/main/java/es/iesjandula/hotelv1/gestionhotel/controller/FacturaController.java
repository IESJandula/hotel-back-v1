//Paquete.
package es.iesjandula.hotelv1.gestionhotel.controller;
//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.exception.ReservaNotFoundException;
import es.iesjandula.hotelv1.gestionhotel.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar las operaciones de las facturas.
 */
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    //Atributos
    @Autowired
    private FacturaService facturaService;

    /**
     * Genera una factura a partir de una reserva identificada por su ID.
     * @param idReserva El ID de la reserva para la cual se generará la factura.
     * @return La factura generada.
     */
    @PostMapping("/generar/{idReserva}")
    public ResponseEntity<Factura> generarFactura(@PathVariable Long idReserva) {
        try {
            Factura factura = facturaService.generarFactura(idReserva);
            return new ResponseEntity<>(factura, HttpStatus.CREATED);  // Retorna el 201 (Creado)
        } catch (ReservaNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // 404 si no se encuentra la reserva
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 en caso de otros errores
        }
    }
}
