//Paquete.
package es.iesjandula.hotelv1.gestionhotel.repository;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definición de la interface RepositoryPago.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    //Métodos Personalizados.

}
