//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model;

//Importaciones.
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Definición de la entidad Factura.
 */
@Entity
public class Factura {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación OneToOne con Reserva
    @OneToOne
    @JoinColumn(name = "reserva_id", referencedColumnName = "id", nullable = false)  // Relación obligatoria con Reserva
    private Reserva reserva;

    // Relación ManyToOne con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)  // Relación obligatoria con Cliente
    private Cliente cliente;

    private double total;

    // La fecha de emisión no debe ser nula en la base de datos
    private LocalDate fechaEmision;

    // Constructor vacío obligatorio
    public Factura() {}

    /**
     * Constructor para la entidad Factura con los siguientes parámetros.
     * @param reserva La reserva asociada a la factura.
     * @param cliente El cliente que realizó la reserva.
     * @param total El total de la factura.
     * @param fechaEmision La fecha de emisión de la factura.
     */
    public Factura(Reserva reserva, Cliente cliente, double total, LocalDate fechaEmision) {
        this.reserva = reserva;
        this.cliente = cliente;
        this.total = total;
        this.fechaEmision = fechaEmision;
    }

    // Métodos de acceso.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    // Método toString() sobreescrito para mostrar solo información relevante
    @Override
    public String toString() {
        return String.format(
                "Factura ID: %d\n" +
                        "Cliente: %s\n" +
                        "Reserva ID: %d\n" +
                        "Total: %.2f\n" +
                        "Fecha de Emisión: %s",
                id,
                cliente != null ? cliente.getNombre() : "No asignado",
                reserva != null ? reserva.getId() : "No asignada",
                total,
                fechaEmision != null ? fechaEmision.toString() : "No asignada"
        );
    }
}
