package es.iesjandula.hotelv1.gestionhotel.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Definición de la clase ReservaHabitaciónId implementada de Serializable.
 */
@Embeddable
public class ReservaHabitacionId implements Serializable {

    @Column(name = "reserva_id")
    private Long reservaId;

    @Column(name = "habitacion_id")
    private Long habitacionId;

    // Constructor sin argumentos
    public ReservaHabitacionId() {}

    // Constructor con argumentos
    public ReservaHabitacionId(Long reservaId, Long habitacionId) {
        this.reservaId = reservaId;
        this.habitacionId = habitacionId;
    }

    // Getters y Setters
    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public Long getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(Long habitacionId) {
        this.habitacionId = habitacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservaHabitacionId that = (ReservaHabitacionId) o;
        return Objects.equals(reservaId, that.reservaId) &&
                Objects.equals(habitacionId, that.habitacionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservaId, habitacionId);
    }
}
