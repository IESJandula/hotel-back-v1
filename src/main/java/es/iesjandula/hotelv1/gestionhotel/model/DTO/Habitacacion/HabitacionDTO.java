package es.iesjandula.hotelv1.gestionhotel.model.DTO.Habitacacion;

import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.TipoHabitacion;

public class HabitacionDTO {

    private String numero; // Número de la habitación
    private TipoHabitacion tipo; // Tipo de habitación (enum)
    private EstadoHabitacion estado; // Estado de la habitación (enum)

    // Constructor vacío
    public HabitacionDTO() {
    }

    // Constructor con parámetros
    public HabitacionDTO(String numero, TipoHabitacion tipo, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.estado = estado;
    }

    // Getters y Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    // Método toString (opcional, útil para depuración)
    @Override
    public String toString() {
        return "HabitacionDTO{" +
                "numero='" + numero + '\'' +
                ", tipo=" + tipo +
                ", estado=" + estado +
                '}';
    }
}

