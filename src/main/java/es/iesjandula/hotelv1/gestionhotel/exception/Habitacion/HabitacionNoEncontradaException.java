package es.iesjandula.hotelv1.gestionhotel.exception.Habitacion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HabitacionNoEncontradaException extends RuntimeException {
    public HabitacionNoEncontradaException(String message) {
        super(message);
    }
}
