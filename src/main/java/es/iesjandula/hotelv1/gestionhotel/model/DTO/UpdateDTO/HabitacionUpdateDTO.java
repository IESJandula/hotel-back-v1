package es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO;

import es.iesjandula.hotelv1.gestionhotel.model.Enum.EstadoHabitacion;
import es.iesjandula.hotelv1.gestionhotel.model.Enum.TipoHabitacion;

public class HabitacionUpdateDTO {

    private String numero; // Número de la habitación
    private TipoHabitacion tipo; // Tipo de la habitación
    private Double precio; // Precio de la habitación
    private Integer capacidad; // Capacidad de la habitación
    private EstadoHabitacion estado; // Estado de la habitación
    private Long hotelId; // ID del hotel al que se asigna la habitación

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}
