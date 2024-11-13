//Paquete.
package es.iesjandula.hotelv1.gestionhotel.repository;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Definición de la interfaz FacturaRepository extendida de JpaRepository.
 */
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas.
}
