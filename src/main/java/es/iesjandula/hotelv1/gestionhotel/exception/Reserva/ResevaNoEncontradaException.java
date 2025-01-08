package es.iesjandula.hotelv1.gestionhotel.exception.Reserva;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResevaNoEncontradaException extends RuntimeException {
    public ResevaNoEncontradaException(String message) {
        super(message);
    }
}
