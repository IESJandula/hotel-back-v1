package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.model.DTO.Factura.FacturaCrearDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.service.FacturaService;
import es.iesjandula.hotelv1.gestionhotel.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaRepository facturaRepository;

    // Endpoint para crear una nueva factura
    @PostMapping()
    public ResponseEntity<?> crearFactura(@RequestBody FacturaCrearDTO facturaDTO) {
        try {
            // Llamamos al servicio para crear la factura
            byte[] pdfFactura = facturaService.crearFactura(facturaDTO);

            // Retornamos el PDF generado como un archivo adjunto
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=factura_" + facturaDTO.getReservaId() + ".pdf")
                    .body(pdfFactura);
        } catch (IOException e) {
            // Si ocurre algún error durante la creación de la factura
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar la factura PDF: " + e.getMessage());
        }
    }

    // Obtener todas las facturas
    @GetMapping
    public List<Factura> obtenerFacturas() {
        return facturaRepository.findAll();
    }

    // Obtener una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        return factura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar factura
    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable Long id, @RequestBody Factura facturaActualizada) {
        if (!facturaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        facturaActualizada.setId(id);
        Factura facturaGuardada = facturaRepository.save(facturaActualizada);
        return ResponseEntity.ok(facturaGuardada);
    }

    // Eliminar factura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        if (!facturaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        facturaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build(); // No hay contenido
        }
        return ResponseEntity.ok(facturas); // 200 OK con la lista de facturas
    }

    // Obtener una factura por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Long id) {
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        return factura.map(ResponseEntity::ok) // Si existe, retorna 200 OK con la factura
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no existe, retorna 404 Not Found
    }



}
