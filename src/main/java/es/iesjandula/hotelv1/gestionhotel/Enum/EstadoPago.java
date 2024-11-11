package es.iesjandula.hotelv1.gestionhotel.Enum;

public enum EstadoPago {
    PENDIENTE("Pendiente"),
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


    public static EstadoPago fromNombre(String nombre) {
        for (EstadoPago estado : EstadoPago.values()) {
            if (estado.getNombre().equalsIgnoreCase(nombre)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de pago no válido: " + nombre);
    }


    @Override
    public String toString() {
        return getNombre();
    }
}

