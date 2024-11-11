package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.exception.ResourceNotFoundException;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    // Metodo para crear una reserva
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Método para obtener una reserva por ID
    public Reserva obtenerReservaPorId(long idReserva) {
        // Buscamos la reserva en el repositorio usando el ID
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);

        // Si la reserva existe, la devolvemos; de lo contrario, lanzamos una excepción
        return reservaOpt.orElseThrow(() -> new RuntimeException("Reserva no encontrada con el ID: " + idReserva));
    }

    //Metodo para Actualizar reserva
    public Reserva actualizarReserva(Long id, Reserva nuevosDatos) throws ResourceNotFoundException {
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setId(nuevosDatos.getId());
        reserva.setCliente(nuevosDatos.getCliente());
        reserva.setFechaInicio(nuevosDatos.getFechaInicio());
        reserva.setFechaFin(nuevosDatos.getFechaFin());
        return reservaRepository.save(reserva);
    }

    //metodo para eliminar una reserva
    public void eliminarReserva(Long id) throws ResourceNotFoundException {
        Reserva reserva = obtenerReservaPorId(id);
        reservaRepository.delete(reserva);
    }





}

