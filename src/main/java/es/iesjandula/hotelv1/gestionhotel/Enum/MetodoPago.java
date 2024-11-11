//Paquete.
package es.iesjandula.hotelv1.gestionhotel.Enum;

/**
 * Definición de la clase enumerada MetodoPago.
 */
public enum MetodoPago {
    //Definimos los posibles estados del Método de pago.
    TARJETA_CREDITO("Tarjeta de Credito"),
    TARJETA_DEBITO("Tarjeta de Debito"),
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia"),
    PAYPAL("Paypal"),;

    //Atributo nombre del estado.
    private final String nombre;

    /**
     * Constructor de la clase enumerada MetodoPago con el siguiente parámetro.
     * @param nombre
     */
    MetodoPago(String nombre) {
        this.nombre = nombre;
    }

    //Método de acceso.
    public String getNombre() {
        return nombre;
    }


    //Método toString sobrescrito.
    @Override
    public String toString() {
        return getNombre();
    } // Devuelve el nombre del estado, utilizando el getter
}
