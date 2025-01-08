//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model;
//Importaciones.
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoReserva;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Reserva {
    //Atributos de la clase.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;
    private Double precioPorNoche;

    //Relaciones.

    //Cliente → Reserva (1:N)
    @ManyToOne
    @JoinColumn(name ="clienteReserva")
    @JsonIgnore
    private Cliente clienteReserva;


    //Reserva → Habitacion (N:M): Relación de muchos a muchos.
    @ManyToMany
    @JoinTable(
            name = "Reserva_Habitacion", //Tabla intermedia.
            joinColumns = @JoinColumn(name = "reserva_id"), // Columna para la clave foránea de Reserva
            inverseJoinColumns = @JoinColumn(name = "habitacion_id") // Columna para la clave foránea de Habitacion
    )
    @JsonIgnore
    private List<Habitacion> reservasHabitaciones ;



    //Reserva → Factura (1:1): Una reserva tiene una única factura.

    @OneToOne(mappedBy = "reservaFactura", cascade = CascadeType.ALL)
    @JsonIgnore
    private Factura reservaFactura;



    //Reserva → Pago (1:N): Una reserva puede tener varios pagos.
    @OneToMany(mappedBy = "reservasPago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> reservasPagos ;




    // Relación muchos a uno con Cliente
    @ManyToOne
    @JoinColumn(name = "hotelReserva")
    private Hotel hotelReserva;


    public Reserva() {
        //Constructor vacío (Obligatorio en JPA).
    }

    //Constructor con los atributos de la entidad.
    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estadoReserva, Double precioPorNoche) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = estadoReserva;
        this.precioPorNoche = precioPorNoche;
    }

    //Constructor con los atributos de las relaciones.

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estadoReserva, Double precioPorNoche, Cliente clienteReserva, List<Habitacion> reservasHabitaciones, Factura reservaFactura, List<Pago> reservasPagos) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = estadoReserva;
        this.precioPorNoche = precioPorNoche;
        this.clienteReserva = clienteReserva;
        this.reservasHabitaciones = reservasHabitaciones;
        this.reservaFactura = reservaFactura;
        this.reservasPagos = reservasPagos;
    }

    //Getter y Setters.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.estadoReserva = estadoReserva;
    }

    public Double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(Double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public Cliente getClienteReserva() {
        return clienteReserva;
    }

    public void setClienteReserva(Cliente clienteReserva) {
        this.clienteReserva = clienteReserva;
    }

    public List<Habitacion> getReservasHabitaciones() {
        return reservasHabitaciones;
    }

    public void setReservasHabitaciones(List<Habitacion> reservasHabitaciones) {
        this.reservasHabitaciones = reservasHabitaciones;
    }

    public Factura getReservaFactura() {
        return reservaFactura;
    }

    public void setReservaFactura(Factura reservaFactura) {
        this.reservaFactura = reservaFactura;
    }

    public List<Pago> getReservasPagos() {
        return reservasPagos;
    }

    public void setReservasPagos(List<Pago> reservasPagos) {
        this.reservasPagos = reservasPagos;
    }

    public Hotel getHotelReserva() {
        return hotelReserva;
    }

    public void setHotelReserva(Hotel hotelReserva) {
        this.hotelReserva = hotelReserva;
    }
}
