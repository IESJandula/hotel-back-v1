package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Método para crear una nueva reserva
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva crearReserva(@RequestParam Long clienteId,
                                @RequestParam int numeroHabitaciones,
                                @RequestParam String fechaInicio,
                                @RequestParam String fechaFin) {
        // Aquí convendría convertir las fechas desde String a LocalDate
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reservaService.crearReserva(clienteId, numeroHabitaciones, inicio, fin);
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
