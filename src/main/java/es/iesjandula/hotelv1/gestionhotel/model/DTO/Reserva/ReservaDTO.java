//Paquete.
package es.iesjandula.hotelv1.gestionhotel.model.DTO.Reserva;

//Importaciones.
import java.time.LocalDate;
import java.util.List;

public class ReservaDTO {

    private Long hotelReserva;
    private Long clienteId;
    private List<Long> habitacionIds;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Getters y setters

    public Long getHotelReserva() {
        return hotelReserva;
    }

    public void setHotelReserva(Long hotelReserva) {
        this.hotelReserva = hotelReserva;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<Long> getHabitacionIds() {
        return habitacionIds;
    }

    public void setHabitacionIds(List<Long> habitacionIds) {
        this.habitacionIds = habitacionIds;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
