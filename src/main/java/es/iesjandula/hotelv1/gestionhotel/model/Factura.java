package es.iesjandula.hotelv1.gestionhotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoFactura;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Factura {

    // Atributos de la clase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;
    private LocalDate fechaEmision;

    // Nuevo atributo descuento
    private Double descuento;

    // Estado de la factura
    @Enumerated(EnumType.STRING)
    private EstadoFactura estadoFactura;

    // Relaciones

    // Cliente → Factura (1:N)
    @ManyToOne
    @JoinColumn(name ="clienteFactura")
    @JsonIgnore
    private Cliente clienteFactura;

    // Reserva → Factura (1:1): Una reserva tiene una única factura.
    @OneToOne
    @JoinColumn(name = "reservaFactura")
    @JsonIgnore
    private Reserva reservaFactura;

    // Constructor vacío (Obligatorio JPA)
    public Factura() {
        // Iniciar estadoFactura con un valor predeterminado si es necesario
        this.estadoFactura = EstadoFactura.PENDIENTE;  // Asegúrate de que tienes un valor predeterminado en el enum
        this.descuento = 0.0; // Inicializar descuento con valor 0.0 por defecto
    }

    // Constructor con los atributos básicos de la entidad
    public Factura(Double total, LocalDate fechaEmision) {
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.estadoFactura = EstadoFactura.PENDIENTE;  // Establecer un valor predeterminado si es necesario
        this.descuento = 0.0; // Inicializar descuento con valor 0.0 por defecto
    }

    // Constructor con los atributos de la entidad y las relaciones
    public Factura(Double total, LocalDate fechaEmision, Cliente clienteFactura, Reserva reservaFactura, EstadoFactura estadoFactura, Double descuento) {
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.clienteFactura = clienteFactura;
        this.reservaFactura = reservaFactura;
        this.estadoFactura = estadoFactura != null ? estadoFactura : EstadoFactura.PENDIENTE;  // Evitar null
        this.descuento = descuento != null ? descuento : 0.0; // Evitar null y asignar valor por defecto
    }

    // Getter y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Cliente getClienteFactura() {
        return clienteFactura;
    }

    public void setClienteFactura(Cliente clienteFactura) {
        this.clienteFactura = clienteFactura;
    }

    public Reserva getReservaFactura() {
        return reservaFactura;
    }

    public void setReservaFactura(Reserva reservaFactura) {
        this.reservaFactura = reservaFactura;
    }

    public EstadoFactura getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(EstadoFactura estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    // Métodos equals y hashCode (útil para comparar entidades y garantizar integridad en colecciones)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(id, factura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString (útil para depuración)
    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", total=" + total +
                ", descuento=" + descuento +  // Mostrar el descuento
                ", fechaEmision=" + fechaEmision +
                ", clienteFactura=" + (clienteFactura != null ? clienteFactura.getId() : "null") +
                ", reservaFactura=" + (reservaFactura != null ? reservaFactura.getId() : "null") +
                ", estadoFactura=" + estadoFactura +
                '}';
    }
}
