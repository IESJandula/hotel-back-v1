package es.iesjandula.hotelv1.gestionhotel.model.DTO.Pagos;

import es.iesjandula.hotelv1.gestionhotel.model.Enum.MetodoPago;

public class DatosDePago {
    private Double monto;                // Monto del pago
    private MetodoPago metodoPago;       // Método de pago (tarjeta, efectivo, etc.)
    private String numeroTarjeta;        // Número de tarjeta (si el método de pago es tarjeta)
    private String nombreTitular;        // Nombre del titular (si aplica)
    private String fechaExpiracion;      // Fecha de expiración de la tarjeta (si aplica)
    private String cvv;                  // Código de seguridad de la tarjeta (si aplica)



    private DatosDePago() {}

    public DatosDePago(Double monto, MetodoPago metodoPago, String numeroTarjeta, String nombreTitular, String fechaExpiracion, String cvv) {
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }


    // Método toString (para depuración)
    @Override
    public String toString() {
        return "DatosDePago{" +
                "monto=" + monto +
                ", metodoPago=" + metodoPago +
                ", numeroTarjeta='" + (numeroTarjeta != null ? "**** **** **** " + numeroTarjeta.substring(numeroTarjeta.length() - 4) : "N/A") + '\'' +
                ", nombreTitular='" + nombreTitular + '\'' +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", cvv='***'" +
                '}';
    }
}


