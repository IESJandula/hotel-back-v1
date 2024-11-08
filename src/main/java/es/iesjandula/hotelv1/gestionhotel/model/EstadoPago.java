package es.iesjandula.hotelv1.gestionhotel.model;

public enum EstadoPago {
    PENDIENTE("Pediente"),
    COMPLETADO("Completado"),
    CANCELADO("Cancelado"),
    FALLIDO("Fallido");
    private final String nombre;

    EstadoPago(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }


    @Override
    public String toString() {
        return getNombre();
    }
}

