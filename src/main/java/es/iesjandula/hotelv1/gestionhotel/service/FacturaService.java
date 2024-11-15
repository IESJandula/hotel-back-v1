// Servicio FacturaService
package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.exception.ReservaNotFoundException;
import es.iesjandula.hotelv1.gestionhotel.repository.FacturaRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    public Factura generarFactura(Long idReserva) throws ReservaNotFoundException {
        // Buscar la reserva en el repositorio
        Reserva reserva = reservaRepository.findById(idReserva).orElseThrow(() -> new ReservaNotFoundException("Reserva no encontrada"));

        // Verificar si ya existe una factura para esta reserva
        Factura existingFactura = facturaRepository.findByReserva(reserva);
        if (existingFactura != null) {
            // Si ya existe, lanzar una excepción o devolver la factura existente
            throw new IllegalStateException("Ya existe una factura generada para esta reserva.");
        }

        // Lógica para generar la factura
        // Asegúrate de que el cálculo del total sea correcto, dependiendo de la lógica de tu modelo
        double total = reserva.getPrecioPorNoche() * (reserva.getFechaFin().compareTo(reserva.getFechaInicio()));

        // Si el total es 0 o incorrecto, verifica los valores de la reserva
        if (total <= 0) {
            throw new IllegalArgumentException("El cálculo del total de la factura es inválido.");
        }

        Factura factura = new Factura();
        factura.setReserva(reserva);
        factura.setCliente(reserva.getCliente());
        factura.setTotal(total);
        factura.setFechaEmision(LocalDate.now());

        // Guardar la factura en la base de datos
        facturaRepository.save(factura);

        return factura;
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();  // Devuelve todas las facturas
    }

    // Método para obtener facturas por cliente
    public List<Factura> obtenerFacturasPorCliente(Long clienteId) {
        return facturaRepository.findByClienteId(clienteId);  // Buscar facturas por clienteId
    }
}