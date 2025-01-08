//Paquete.
package es.iesjandula.hotelv1.gestionhotel.controller;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.ClienteUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.HotelUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import es.iesjandula.hotelv1.gestionhotel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService; //Inyeccion de dependencias.
    @Autowired
    private ClienteRepository clienteRepository;

    // Crear un cliente.
    @PostMapping
    public ResponseEntity<Cliente> crearUsuario(@RequestBody Cliente cliente) {
        try {
            Cliente newCliente = clienteService.crearCliente(cliente);
            return new ResponseEntity<>(newCliente, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener un cliente por su id.
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.obtenerCliente(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    //Obtener un listado de cliente.
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerListaClientes() {
        List<Cliente> clientes = clienteService.obtenerClientes();
        return  ResponseEntity.ok(clientes);
    }



    //Actualizar cliente.
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody ClienteUpdateDTO dto){
        try{
            //Servicio Actualizado del cliente.
            Cliente clienteActualizado = clienteService.actualizarcliente(id, dto);

            //Devuelve el cliente actualizado.
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Borrar cliente.
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> eliminarCliente(@PathVariable Long id){
        try {
            //Llama al servicio.
            clienteService.eliminarCliente(id);

            //Respuesta exitosa, 204 NO_CONTENT
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            //Excepción, estado 400 BAD_REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }



    // Endpoint para listar las reservas de un usuario
    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long id) {
        try {
            List<Reserva> reservas = clienteService.obtenerReservasPorCliente(id);
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
