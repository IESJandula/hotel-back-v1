// Controlador FacturaController
package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.exception.ReservaNotFoundException;
import es.iesjandula.hotelv1.gestionhotel.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/generar/{idReserva}")
    public ResponseEntity<Factura> generarFactura(@PathVariable Long idReserva) {
        try {
            Factura factura = facturaService.generarFactura(idReserva);
            return new ResponseEntity<>(factura, HttpStatus.CREATED);  // Retorna 201 si se genera correctamente
        } catch (ReservaNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // 404 si no se encuentra la reserva
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 en caso de otros errores
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Factura>> obtenerTodasLasFacturas() {
        try {
            List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
            if (facturas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Si no hay facturas, devuelve 204
            }
            return new ResponseEntity<>(facturas, HttpStatus.OK);  // Devuelve las facturas con código 200
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 si ocurre un error
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Factura>> obtenerFacturasPorCliente(@PathVariable Long clienteId) {
        try {
            List<Factura> facturas = facturaService.obtenerFacturasPorCliente(clienteId);
            if (facturas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Si no hay facturas, devuelve 204
            }
            return new ResponseEntity<>(facturas, HttpStatus.OK);  // Devuelve las facturas con código 200
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 si ocurre un error
        }
    }

}