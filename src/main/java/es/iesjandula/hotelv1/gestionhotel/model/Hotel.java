// Paquete
package es.iesjandula.hotelv1.gestionhotel.model;

//Importaciones.
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Hotel {

    //Atributos de la entidad.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Icrementable.
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    private String telefono;

    @Column(nullable = false, length = 50)
    private String direccion;

    private String estrellas;

    @Column(nullable = false, length = 250)
    private String descripcion;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    // Relaciones
    // Hotel - Habitación 1:N
    @OneToMany(mappedBy = "hotelHabitacion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Habitacion> hotelHabitacion;

    // Hotel - Reserva 1:N
    @OneToMany(mappedBy = "hotelReserva", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reserva> hotelReserva;

    // Constructor vacío (Obligatorio en JPA).
    public Hotel() {
    }

    // Constructor con los atributos de la entidad.
    public Hotel(String nombre, String telefono, String direccion,
                 String estrellas, String descripcion, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estrellas = estrellas;
        this.descripcion = descripcion;
        this.email = email;
    }

    // Constructor con los atributos de las relaciones.
    public Hotel(String nombre, String telefono, String direccion, String estrellas, String descripcion, String email, List<Habitacion> hotelHabitacion, List<Reserva> hotelReserva) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estrellas = estrellas;
        this.descripcion = descripcion;
        this.email = email;
        this.hotelHabitacion = hotelHabitacion;
        this.hotelReserva = hotelReserva;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(String estrellas) {
        this.estrellas = estrellas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Habitacion> getHotelHabitacion() {
        return hotelHabitacion;
    }

    public void setHotelHabitacion(List<Habitacion> hotelHabitacion) {
        this.hotelHabitacion = hotelHabitacion;
    }

    public List<Reserva> getHotelReserva() {
        return hotelReserva;
    }

    public void setHotelReserva(List<Reserva> hotelReserva) {
        this.hotelReserva = hotelReserva;
    }
}
