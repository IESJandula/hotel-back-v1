//Paquete.
package es.iesjandula.hotelv1.gestionhotel.Enum;

/**
 * Definición de la clase enumerada EstadoPago.
 */
public enum EstadoPago {
    //Definimos los posibles estados de pago.
    PENDIENTE("Pediente"),
    COMPLETADO("Completado"),
    CANCELADO("Cancelado"),
    FALLIDO("Fallido");

    //Atributo nombre del estado.
    private final String nombre;

    /**
     * Constructor de la enumeración EstadoPago con el siguiente parámetro.
     * @param nombre
     */
    EstadoPago(String nombre) {
        this.nombre = nombre;
    }

    //Método de acceso
    public String getNombre() {
        return nombre;
    }


    //Método toString() sobrescrito
    @Override
    public String toString() {
        return getNombre(); // Devuelve el nombre del estado, utilizando el getter
    }
}

