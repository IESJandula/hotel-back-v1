//Paquete.
package es.iesjandula.hotelv1.gestionhotel.service;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.ClienteUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository; //Inyección de dependencias.


    @Autowired
    private HotelRepository hotelRepository; //Inyección de dependencias.



    // Método para crear un nuevo cliente
    @Transactional
    public Cliente crearCliente(Cliente cliente) {
        // Verificar si el cliente ya existe por correo
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con el correo: " + cliente.getEmail());
        }

        // Guardar el nuevo cliente
        return clienteRepository.save(cliente);
    }

    // Método para obtener un cliente
    public Cliente obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + id + " no encontrado"));
    }


    //Obtener un listado de clientes.
    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }


    //Actualizar un cliente.
    @Transactional
    public Cliente actualizarcliente(Long id, ClienteUpdateDTO dto){

        //Buscamos cliente por id.
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        //Actualizar los valore con la DTO.
        if(dto.getNombre()!=null) clienteExistente.setNombre(dto.getNombre());
        if(dto.getEmail()!=null) clienteExistente.setEmail(dto.getEmail());
        if(dto.getTelefono()!=null) clienteExistente.setTelefono(dto.getTelefono());

        //Guardamos los cambios.
        return clienteRepository.save(clienteExistente);

    }

    @Transactional
    // Método para eliminar un cliente
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }









    // Método para obtener las reservas de un cliente por su ID
    public List<Reserva> obtenerReservasPorCliente(Long id) {
        Cliente cliente = obtenerCliente(id); // Busca el cliente o lanza una excepción si no existe
        return cliente.getClienteReserva(); // Devuelve la lista de reservas del cliente
    }

}
