package es.iesjandula.hotelv1.gestionhotel.model;

import es.iesjandula.hotelv1.gestionhotel.Enum.EstadoHabitacion;
import jakarta.persistence.*;
import java.util.List;

import java.util.List;

@Entity
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String tipo;
    private Double precio;
    private int capacidad;
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado;

    public Habitacion(String numero, String tipo, Double precio, int capacidad, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public Habitacion() {
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                ", capacidad=" + capacidad +
                ", estado=" + estado +
                '}';
    }

    @ManyToMany
    @JoinTable(
            name = "habitacion_reserva",
            joinColumns = @JoinColumn(name = "habitacion_id"),
            inverseJoinColumns = @JoinColumn(name = "reserva_id")
    )
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL)
    private List<ReservaHabitacion> reservaHabitaciones;
}
