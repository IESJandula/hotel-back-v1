//Paquete.
package es.iesjandula.hotelv1.gestionhotel.controller;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.HotelUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import es.iesjandula.hotelv1.gestionhotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelService; //Inyección de dependencia.

    //Crear un hotel
    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody Hotel hotel) {
       try{
           Hotel nuevoHotel = hotelService.crearHotel(hotel, hotel.getEmail());
           return new ResponseEntity<>(nuevoHotel, HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    // Obtener hotel por su id.
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> obtenerHotelPorId(@PathVariable Long id) {
        // Buscamos el hotel
        Optional<Hotel> hotel = hotelService.obtenerHotel(id);
        if (hotel.isPresent()) {
            // Encontrado c200 (OK)
            return new ResponseEntity<>(hotel.get(), HttpStatus.OK);
        } else {
            // No encontrado c404 (No encontrado)
            return ResponseEntity.notFound().build();
        }
    }


    //Obtener un listado de hoteles.
    @GetMapping
    public ResponseEntity<List<Hotel>> obtenerListaHoteles() {
        List<Hotel> hotel = hotelService.obtenerHoteles();
        return  ResponseEntity.ok(hotel);
    }


    //Actualizar el hotel.
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> actualizarHotel(@PathVariable Long id, @RequestBody HotelUpdateDTO dto){
        try{
            //Servicio Actualizado del hotel.
            Hotel hotelActualizado = hotelService.actualizarHotel(id, dto);

            //Devuelve el hotel actualizado.
            return new ResponseEntity<>(hotelActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //Borrado del hotel.
    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> eliminarHotel(@PathVariable Long id){
        try {
            //Llama al servicio.
            hotelService.eliminarHotel(id);

            //Respuesta exitosa, 204 NO_CONTENT
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            //Excepción, estado 400 BAD_REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
}
