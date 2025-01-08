//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model;

//Importaciones.
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente {
    //Atributos de la entidad.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-incremental.
    private Long id;
    @Column(nullable = false, length = 85) //Obligatorio con longitud max 85.
    private String nombre;
    @Column(nullable = false, length = 85) //Obligatorio con longitud max 85.
    private String email;
    private String telefono;

    //Relaciones.

    //Cliente → Reserva (1:N). Un cliente puede tener varias reservas.
    @OneToMany(mappedBy = "clienteReserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reserva> clienteReserva;

     // Cliente → Factura (1:N)
    @OneToMany(mappedBy = "clienteFactura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Factura> clienteFactura;

    public Cliente() {
        //Constructor vacío (Obligatorio en JPA).
    }


    //Constructor con los elementos de la entidad.
    public Cliente(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }


    //Constructor con los elementos de las relaciones.
    public Cliente(String nombre, String email, String telefono, List<Reserva> clienteReserva, List<Factura> clienteFactura) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.clienteReserva = clienteReserva;
        this.clienteFactura = clienteFactura;
    }

    //Getters y Setters.
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Reserva> getClienteReserva() {
        return clienteReserva;
    }

    public void setClienteReserva(List<Reserva> clienteReserva) {
        this.clienteReserva = clienteReserva;
    }

    public List<Factura> getClienteFactura() {
        return clienteFactura;
    }

    public void setClienteFactura(List<Factura> clienteFactura) {
        this.clienteFactura = clienteFactura;
    }
}