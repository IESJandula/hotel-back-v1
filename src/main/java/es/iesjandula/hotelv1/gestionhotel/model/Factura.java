package es.iesjandula.hotelv1.gestionhotel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Definición de la entidad Factura.
 */
@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference  // Evita la serialización recursiva en la relación con Reserva
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference  // Evita la serialización recursiva en la relación con Cliente
    private Cliente cliente;

    private double total;
    private LocalDate fechaEmision;

    // Constructor vacío obligatorio
    public Factura() {}

    /**
     * Constructor para la entidad Factura con los siguientes parámetros.
     * @param reserva La reserva asociada a la factura.
     * @param cliente El cliente que realizó la reserva.
     * @param total El total de la factura.
     * @param fechaEmision La fecha de emisión de la factura.
     */
    public Factura(Reserva reserva, Cliente cliente, double total, LocalDate fechaEmision) {
        this.reserva = reserva;
        this.cliente = cliente;
        this.total = total;
        this.fechaEmision = fechaEmision;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", reserva=" + reserva +
                ", cliente=" + cliente +
                ", total=" + total +
                ", fechaEmision=" + fechaEmision +
                '}';
    }
}