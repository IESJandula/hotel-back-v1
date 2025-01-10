package es.iesjandula.hotelv1.gestionhotel.model.DTO.Factura;

import java.time.LocalDate;

public class FacturaDTO {
    private Long clienteId;   // ID del cliente asociado a la factura
    private Long reservaId;   // ID de la reserva asociada a la factura
    private Double total;     // Total de la factura antes de aplicar cualquier descuento
    private Double descuento; // Descuento aplicado a la factura
    private LocalDate fechaEmision; // Fecha de emisión de la factura

    // Getters y Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

}
