package es.iesjandula.hotelv1.gestionhotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva_habitacion")
public class ReservaHabitacion {

    @EmbeddedId
    private ReservaHabitacionId id;

    //Relaciones Many-to-One con Reserva: Una reserva puede incluir muchas habitaciones.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", insertable = false, updatable = false)
    @MapsId("reservaId")
    private Reserva reserva;

    //Relaciones ManyToOne con habitacion Una habitación puede estar en muchas reservas.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitacion_id", insertable = false, updatable = false)
    @MapsId("habitacionId")
    private Habitacion habitacion;

    // Constructor sin argumentos
    public ReservaHabitacion() {}

    // Constructor con argumentos
    public ReservaHabitacion(Reserva reserva, Habitacion habitacion) {
        this.reserva = reserva;
        this.habitacion = habitacion;
        this.id = new ReservaHabitacionId(reserva.getId(), habitacion.getId());
    }

    // Getters y Setters
    public ReservaHabitacionId getId() {
        return id;
    }

    public void setId(ReservaHabitacionId id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
}

