package es.iesjandula.hotelv1.gestionhotel.exception.Hotel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT) //409 Conflict
public class HotelYaExisteException extends RuntimeException {
    public HotelYaExisteException(String message) {
        super(message);
    }
}
