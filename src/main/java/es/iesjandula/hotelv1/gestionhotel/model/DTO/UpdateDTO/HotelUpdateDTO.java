package es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO;

public class HotelUpdateDTO {

    private String nombre;
    private String telefono;
    private String direccion;
    private String estrellas;
    private String descripcion;
    private String email;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(String estrellas) {
        this.estrellas = estrellas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
