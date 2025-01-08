package es.iesjandula.hotelv1.gestionhotel.exception.Hotel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HotelNoEncontradoException extends RuntimeException {
    public HotelNoEncontradoException(String message) {
        super(message);
    }
}
