//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model.Enum;

/**
 * Definición de la clase enumerada EstadoReserva
 */
public enum EstadoReserva {
    //Definimos los siguientes estados de la reserva.
    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada"),
    FINALIZADA("Finalizada");

    //Atributo nombre de la reserva.
    private final String nombre;

    /**
     * Constructor de la clase enumerada EstadoReserva.
     * @param nombre
     */
    EstadoReserva(String nombre) {
        this.nombre = nombre;
    }

    //Método de acceso.
    public String getNombre() {
        return nombre;
    }


    //Método toString() sobrescrito.
    @Override
    public String toString() {
        return getNombre();
    }

}
