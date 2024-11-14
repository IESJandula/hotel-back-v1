package es.iesjandula.hotelv1.gestionhotel.model;

import es.iesjandula.hotelv1.gestionhotel.Enum.EstadoReserva;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoReserva estadoReserva;  // Campo que mantiene el estado de la reserva
    private double precioPorNoche;

    // Relación Many-to-One con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relación Many-to-Many con Habitacion
    @ManyToMany
    @JoinTable(
            name = "reserva_habitacion",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "habitacion_id")
    )
    private List<Habitacion> habitaciones;

    // Relación One-to-Many con Pago
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    // Relación One-to-Many con ReservaHabitacion
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ReservaHabitacion> reservaHabitaciones;

    // Relación One-to-One con Factura
    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Factura factura;

    // Constructor vacío
    public Reserva() {}

    // Constructor con parámetros
    public Reserva(long id, LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estadoReserva, Cliente cliente, double precioPorNoche) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = estadoReserva; // Aquí se asigna el estado de la reserva
        this.cliente = cliente;
        this.precioPorNoche = precioPorNoche;
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;  // Se usa este setter para cambiar el estado de la reserva
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<ReservaHabitacion> getReservaHabitaciones() {
        return reservaHabitaciones;
    }

    public void setReservaHabitaciones(List<ReservaHabitacion> reservaHabitaciones) {
        this.reservaHabitaciones = reservaHabitaciones;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    // Método para calcular el total de la reserva
    public double getTotal() {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return precioPorNoche * dias;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estadoReserva=" + estadoReserva +
                ", cliente=" + cliente +
                ", precioPorNoche=" + precioPorNoche +
                ", habitaciones=" + habitaciones +
                ", pagos=" + pagos +
                ", reservaHabitaciones=" + reservaHabitaciones +
                ", factura=" + factura +
                '}';
    }
}
