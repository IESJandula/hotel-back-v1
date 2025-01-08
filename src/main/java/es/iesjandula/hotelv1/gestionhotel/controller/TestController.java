package es.iesjandula.hotelv1.gestionhotel.controller;

import es.iesjandula.hotelv1.gestionhotel.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PagoRepository pagoRepository;

    @GetMapping("/conexion")
    public String pruebaConexion() {
        long count = pagoRepository.count();
        return "Conexión exitosa. Total de registros en la tabla Pago: " + count;
    }
}

