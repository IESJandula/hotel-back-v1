// Paquete
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
    private EstadoReserva reserva;
    private double precioPorNoche;  // Campo para almacenar el precio por noche

    // Relación Many-to-One con Cliente: Una reserva está asociada a un único cliente.
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relación Many-to-Many con Habitacion: Una reserva puede incluir muchas habitaciones
    @ManyToMany
    @JoinTable(
            name = "reserva_habitacion", // Nombre de la tabla de unión
            joinColumns = @JoinColumn(name = "reserva_id"), // Columna de unión para Reserva
            inverseJoinColumns = @JoinColumn(name = "habitacion_id") // Columna de unión para Habitacion
    )
    private List<Habitacion> habitaciones;

    // Relación One-to-Many con Pago: Una reserva puede tener muchos pagos
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    // Relación One-to-Many con ReservaHabitacion
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ReservaHabitacion> reservaHabitaciones;

    // Relación One-to-One con Factura
    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Factura factura;

    // Constructor vacío
    public Reserva() {
    }

    // Constructor con parámetros
    public Reserva(long id, LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva reserva, Cliente cliente, double precioPorNoche) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.reserva = reserva;
        this.cliente = cliente;
        this.precioPorNoche = precioPorNoche;  // Asignación del precio por noche
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

    public EstadoReserva getReserva() {
        return reserva;
    }

    public void setReserva(EstadoReserva reserva) {
        this.reserva = reserva;
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

    public double getTotal() {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin); // Calcula los días entre la fecha de inicio y fin
        return precioPorNoche * dias; // Multiplica el precio por noche por los días
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", reserva=" + reserva +
                ", cliente=" + cliente +
                ", precioPorNoche=" + precioPorNoche +
                ", habitaciones=" + habitaciones +
                ", pagos=" + pagos +
                ", reservaHabitaciones=" + reservaHabitaciones +
                ", factura=" + factura +
                '}';
    }
}
