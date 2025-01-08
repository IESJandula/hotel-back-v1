package es.iesjandula.hotelv1.gestionhotel.exception.Cliente;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(String message) {
        super(message);
    }
}
