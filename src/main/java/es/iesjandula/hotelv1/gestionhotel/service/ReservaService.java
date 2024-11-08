package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    // Metodo para crear una reserva
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}
