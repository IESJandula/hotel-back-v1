package es.iesjandula.hotelv1.gestionhotel.service;

import es.iesjandula.hotelv1.gestionhotel.exception.ResourceNotFoundException;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    //Metodo para crear un usuario
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    //Metodo para obtener un cliente
    public Cliente obtenerCliente(Long id) throws ResourceNotFoundException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));
    }

    //Metodo para actualizar cliente
    public Cliente actualizarCliente(Long id, Cliente nuevosDatos) throws ResourceNotFoundException {
        Cliente cliente = obtenerCliente(id);
        cliente.setNombre(nuevosDatos.getNombre());
        cliente.setEmail(nuevosDatos.getEmail());
        return clienteRepository.save(cliente);
    }

    //metodo para eliminar un cliente
    public void eliminarCliente(Long id) throws ResourceNotFoundException {
        Cliente cliente = obtenerCliente(id);
        clienteRepository.delete(cliente);

    }
}
