//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.Enum.MetodoPago;
import jakarta.persistence.*;
import java.util.Date;

/**
 * Definición de la entidad pago.
 */
@Entity
@Table(name="Pago")
public class Pago {

    //Atributos.
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private double monto;
    private Date fecha;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;
    @Enumerated(EnumType.STRING)
    private EstadoPago estado;
    private Reserva reserva;


    //Relaciones.
    // Relación ManyToOne con Reserva.
    // Un pago está asociado a una única reserva.
    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    //Constructor vacío
    public Pago() {
    }

    /**
     * Constructor de la clase (Entidad) Pago con los siguientes parámetros.
     * @param id
     * @param monto
     * @param fecha
     * @param metodo
     * @param estado
     * @param reserva
     */

    public Pago(long id, double monto, Date fecha, MetodoPago metodo, EstadoPago estado, Reserva reserva) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
        this.estado = estado;
        this.reserva = reserva;
    }

    //Métodos de acceso.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    //Método toString() sobrescrito.
    @Override
    public String toString() {
        return String.format(
                "id: %d, monto: %.2f, fecha: %s, metodo: %s, estado: %s, reserva: %s",
                id,
                monto,
                (fecha != null ? fecha.toString() : "null"),
                (metodo != null ? metodo.toString() : "null"),
                (estado != null ? estado.toString() : "null"),
                (reserva != null ? reserva.toString() : "null")
        );
    }

}
