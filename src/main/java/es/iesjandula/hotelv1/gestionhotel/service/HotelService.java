//Paquete.
package es.iesjandula.hotelv1.gestionhotel.service;

//Importaciones.
import es.iesjandula.hotelv1.gestionhotel.exception.Hotel.HotelYaExisteException;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.UpdateDTO.HotelUpdateDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Hotel;
import es.iesjandula.hotelv1.gestionhotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository; //Inyección de dependencias.

    //Creamos un hotel.
    @Transactional
    public Hotel crearHotel(Hotel hotel, String email)  {
        //Verificamos si el hotel ya existe por email.
        Optional<Hotel> newHotel = hotelRepository.findByEmail(email);
        if(newHotel.isPresent()){
            throw new HotelYaExisteException("El Hotel ya existe");
        }
        //Si no existe, guardamos el nuevo hotel.
        return hotelRepository.save(hotel);
    }



    // Método para obtener hotel por Id
    public Optional<Hotel> obtenerHotel(Long id) {
        return hotelRepository.findById(id); // `findById` devuelve un Optional
    }


    //Obtener un listado de hoteles.
    public List<Hotel> obtenerHoteles() {
        return hotelRepository.findAll();
    }


    //Actualizar hotel.
    @Transactional
    public Hotel actualizarHotel(Long id,HotelUpdateDTO dto){

        //Buscamos hotel por id.
        Hotel hotelExistente = hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Hotel no encontrado"));

        //Actualizar los valore con la DTO.
        if(dto.getNombre()!=null) hotelExistente.setNombre(dto.getNombre());
        if(dto.getTelefono()!=null) hotelExistente.setTelefono(dto.getTelefono());
        if(dto.getDireccion()!=null) hotelExistente.setDireccion(dto.getDireccion());
        if(dto.getEstrellas()!=null) hotelExistente.setEstrellas(dto.getEstrellas());
        if(dto.getDescripcion()!=null) hotelExistente.setDescripcion(dto.getDescripcion());
        if(dto.getEmail()!=null) hotelExistente.setEmail(dto.getEmail());

        //Guardamos los cambios.
        return hotelRepository.save(hotelExistente);

    }

    //Eliminamos el hotel.
    @Transactional
    public void eliminarHotel(Long id){
        hotelRepository.deleteById(id);
    }


}
