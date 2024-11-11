package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.exception.ResourceNotFoundException;
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

    //Metodo para obtener una reserva
    public Reserva obtenerReserva(Long id) throws ResourceNotFoundException {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("reserva no encontrada"));
    }

    //Metodo para Actualizar reserva
    public Reserva actualizarReserva(Long id, Reserva nuevosDatos) throws ResourceNotFoundException {
        Reserva reserva = obtenerReserva(id);
        reserva.setId(nuevosDatos.getId());
        reserva.setCliente(nuevosDatos.getCliente());
        reserva.setFechaInicio(nuevosDatos.getFechaInicio());
        reserva.setFechaFin(nuevosDatos.getFechaFin());
        return reservaRepository.save(reserva);
    }

    //metodo para eliminar una reserva
    public void eliminarReserva(Long id) throws ResourceNotFoundException {
        Reserva reserva = obtenerReserva(id);
        reservaRepository.delete(reserva);

    }



}

