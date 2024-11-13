package es.iesjandula.hotelv1.gestionhotel.controller;


import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Cliente> crearUsuario(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.crearCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    // Endpoint para obtener información de un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.obtenerCliente(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para listar las reservas de un usuario
    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long id) {
        try {
            List<Reserva> reservas = clienteService.obtenerReservasPorCliente(id);
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
