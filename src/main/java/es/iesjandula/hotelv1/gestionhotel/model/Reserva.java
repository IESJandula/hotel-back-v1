package es.iesjandula.hotelv1.gestionhotel.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fechaInicio;
    private Date fechaFin;
    private Cliente cliente;
    private EstadoReserva reserva;

    public Reserva() {
    }

    public Reserva(long id, Date fechaInicio, Date fechaFin, Cliente cliente, EstadoReserva reserva) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cliente = cliente;
        this.reserva = reserva;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoReserva getReserva() {
        return reserva;
    }

    public void setReserva(EstadoReserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", cliente=" + cliente +
                ", reserva=" + reserva +
                '}';
    }


}
