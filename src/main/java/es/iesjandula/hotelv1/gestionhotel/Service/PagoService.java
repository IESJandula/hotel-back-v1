// Paquete.
package es.iesjandula.hotelv1.gestionhotel.Service;

// Importaciones.
import es.iesjandula.hotelv1.gestionhotel.Enum.EstadoPago;
import es.iesjandula.hotelv1.gestionhotel.Model.Pago;
import es.iesjandula.hotelv1.gestionhotel.Repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Definición del servicio de Pago.
 */
@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    // Método para crear un pago.
    @Transactional
    public Pago crearPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    // Método para obtener un pago por ID.
    public Pago obtenerPago(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));
    }

    // Método para actualizar el estado de un pago.
    @Transactional
    public Pago actualizarEstadoPago(Long id, EstadoPago nuevoEstado) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + id));
        pago.setEstado(nuevoEstado);
        return pagoRepository.save(pago);
    }

    // Método para eliminar un pago por ID.
    @Transactional
    public void eliminarPago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
    }

    // Método para obtener todos los pagos.
    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }
}
