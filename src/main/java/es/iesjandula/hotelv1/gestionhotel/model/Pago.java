//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model;

//Importaciones.
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.MetodoPago;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Pago {
    //Atributos de la clase.
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Double monto;
    private Date fecha;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;
    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    //Relaciones.
    //Reserva → Pago (1:N): Una reserva puede tener varios pagos.
    @ManyToOne
    @JoinColumn(name ="reservasPago")
    @JsonIgnore
    private Reserva reservasPago;


    public Pago() {
        //Cosntructor vacío (Obligatorio para JPA).
    }

    //Constructo con los atributos de la entidad.
    public Pago(Double monto, Date fecha, MetodoPago metodo, EstadoPago estado) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
        this.estado = estado;
    }

    //Constructor con los atributos de las relaciones.
    public Pago(Double monto, Date fecha, MetodoPago metodo, EstadoPago estado, Reserva reservasPago) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
        this.estado = estado;
        this.reservasPago = reservasPago;
    }

    //Getters y Setters.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
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

    public Reserva getReservasPago() {
        return reservasPago;
    }

    public void setReservasPago(Reserva reservasPago) {
        this.reservasPago = reservasPago;
    }

}
