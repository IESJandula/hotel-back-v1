//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model;

//Importaciones.
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.TipoHabitacion;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
public class Habitacion {
    //Atributos de la clase.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) //Obligatorio.
    private String numero;
    @Enumerated(EnumType.STRING)
    private TipoHabitacion tipo;
    private Double precio;
    private Integer capacidad;
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado;

    //Relaciones.

    //Reserva → Habitacion (N:M): Relación de muchos a muchos.

    @ManyToMany(mappedBy = "reservasHabitaciones") // Hace referencia al campo de la entidad Reserva
    @JsonIgnore
    private List<Reserva> reservasHabitaciones;




    // Relación muchos a uno con Cliente
    @ManyToOne
    @JoinColumn(name = "hotelHabitacion")
    @JsonIgnore
    private Hotel hotelHabitacion;




    public Habitacion() {
        //Constructor vacío (Obligatorio JPA).
    }

    //Constructor con los atributos de la entidad.
    public Habitacion(String numero, TipoHabitacion tipo, Double precio, Integer capacidad, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    //Constructor con los atributos de las relaciones.

    public Habitacion(String numero, TipoHabitacion tipo, Double precio, Integer capacidad, EstadoHabitacion estado, List<Reserva> reservasHabitaciones) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
        this.estado = estado;
        this.reservasHabitaciones = reservasHabitaciones;
    }

    //Getters y Setters.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public List<Reserva> getReservasHabitaciones() {
        return reservasHabitaciones;
    }

    public void setReservasHabitaciones(List<Reserva> reservasHabitaciones) {
        this.reservasHabitaciones = reservasHabitaciones;
    }

    public Hotel getHotelHabitacion() {
        return hotelHabitacion;
    }

    public void setHotelHabitacion(Hotel hotelHabitacion) {
        this.hotelHabitacion = hotelHabitacion;
    }
}
