package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.DTO.ReservaDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.model.Habitacion;
import es.iesjandula.hotelv1.gestionhotel.service.ReservaService;
import es.iesjandula.hotelv1.gestionhotel.service.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    // Endpoint para obtener una reserva por ID
    @GetMapping("/obtener/{id}")
    public Reserva obtenerReservaPorId(@PathVariable long id) {
        return reservaService.obtenerReservaPorId(id);
    }
}
