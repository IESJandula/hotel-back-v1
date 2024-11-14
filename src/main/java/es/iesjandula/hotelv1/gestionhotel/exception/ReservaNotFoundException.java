// ReservaNotFoundException.java
package es.iesjandula.hotelv1.gestionhotel.exception;

public class ReservaNotFoundException extends RuntimeException {
    public ReservaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
