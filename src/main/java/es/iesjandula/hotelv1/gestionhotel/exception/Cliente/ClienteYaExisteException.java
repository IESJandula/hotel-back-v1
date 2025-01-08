package es.iesjandula.hotelv1.gestionhotel.exception.Cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT) //409 Conflict
public class ClienteYaExisteException extends RuntimeException {
    public ClienteYaExisteException(String message) {
        super(message);
    }
}
