//Paquete.
package es.iesjandula.hotelv1.gestionhotel.Enum;

/**
 * Definición de la clase MetodoPago.
 */
public enum MetodoPago {
    TARJETA_CREDITO("Tarjeta de Credito"),
    TARJETA_DEBITO("Tarjeta de Debito"),
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia"),
    PAYPAL("Paypal"),;

    //Atributo
    private final String nombre;

    /**
     * Constructor de la clase
     * @param nombre
     */
    MetodoPago(String nombre) {
        this.nombre = nombre;
    }

    //Método de acceso.
    public String getNombre() {
        return nombre;
    }


    //Método toString sobreescrito.
    @Override
    public String toString() {
        return getNombre();
    }
}
