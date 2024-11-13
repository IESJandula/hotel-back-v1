package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    //Metodo para crear un cliente
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Método para obtener un cliente - lanzamos una excepcion si el cliente no es encontrado
    public Cliente obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + id + " no encontrado"));
    }

    // Método para actualizar cliente
    public Cliente actualizarCliente(Long id, Cliente nuevosDatos) {
        Cliente cliente = obtenerCliente(id);
        cliente.setNombre(nuevosDatos.getNombre());
        cliente.setEmail(nuevosDatos.getEmail());
        cliente.setTelefono(nuevosDatos.getTelefono());
        return clienteRepository.save(cliente);
    }

    // Método para eliminar un cliente
    public void eliminarCliente(Long id) {
        Cliente cliente = obtenerCliente(id);
        clienteRepository.delete(cliente);
    }

    // Método para obtener las reservas de un cliente por su ID
    public List<Reserva> obtenerReservasPorCliente(Long id) {
        Cliente cliente = obtenerCliente(id); // Busca el cliente o lanza una excepción si no existe
        return cliente.getReservas(); // Devuelve la lista de reservas del cliente
    }
}
