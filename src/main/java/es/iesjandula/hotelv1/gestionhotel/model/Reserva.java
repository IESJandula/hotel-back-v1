package es.iesjandula.hotelv1.gestionhotel.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fechaInicio;
    private Date fechaFin;
    private EstadoReserva reserva;

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

    // Relacion One-to-Many con pago: una reserva puede tener muchos pagos.
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    //Constructor vacio
    public Reserva() {
    }

    public Reserva(long id, Date fechaInicio, Date fechaFin, EstadoReserva reserva, Cliente cliente) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.reserva = reserva;
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", reserva=" + reserva +
                ", cliente=" + cliente +
                '}';
    }
}
