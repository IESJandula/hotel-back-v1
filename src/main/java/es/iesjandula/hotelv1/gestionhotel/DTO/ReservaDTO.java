package es.iesjandula.hotelv1.gestionhotel.DTO;


import java.time.LocalDate;

public class ReservaDTO {

    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int numeroHabitaciones;

    public ReservaDTO() {}

    public ReservaDTO(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHabitaciones) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numeroHabitaciones = numeroHabitaciones;
    }

    // Getters y setters

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public void setNumeroHabitaciones(int numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }
}
