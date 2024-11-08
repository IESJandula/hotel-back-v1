//Paquete.
package es.iesjandula.hotelv1.gestionhotel.Service;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.EstadoPago;
import es.iesjandula.hotelv1.gestionhotel.model.Pago;
import es.iesjandula.hotelv1.gestionhotel.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Definición del servicio de Pago.
 */
@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    //Método para crear un pago.
    @Transactional
    public Pago crearPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    //Método para obtenerPago.
    public Pago obtenerPago(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));
    }

    //Método para actualizar pago.
    @Transactional
    public Pago actualizarEstadoPago(Long id, EstadoPago nuevoEstado) {
        // Buscar el pago por ID
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + id));

        // Actualizar el estado del pago
        pago.setEstado(nuevoEstado);

        // Guardar el pago actualizado en el repositorio
        return pagoRepository.save(pago);
    }

    //Método para eliminarPago.
    @Transactional
    public void eliminarPago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
    }

}
