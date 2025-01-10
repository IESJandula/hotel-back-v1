package es.iesjandula.hotelv1.gestionhotel.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import es.iesjandula.hotelv1.gestionhotel.exception.Cliente.ClienteNoEncontradoException;
import es.iesjandula.hotelv1.gestionhotel.exception.Factura.FacturaNoEncontradaException;
import es.iesjandula.hotelv1.gestionhotel.exception.Reserva.ResevaNoEncontradaException;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Factura.FacturaCrearDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.FacturaRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public byte[] crearFactura(FacturaCrearDTO facturaDTO) throws IOException {
        // Verificamos si el cliente existe
        Cliente cliente = clienteRepository.findById(facturaDTO.getClienteId())
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));

        // Verificamos si la reserva existe
        Reserva reserva = reservaRepository.findById(facturaDTO.getReservaId())
                .orElseThrow(() -> new ResevaNoEncontradaException("Reserva no encontrada"));

        // Creamos la nueva factura
        Factura factura = new Factura();
        factura.setClienteFactura(cliente);
        factura.setReservaFactura(reserva);

        // Aplicamos el descuento, si existe
        double totalConDescuento = facturaDTO.getTotal();
        if (facturaDTO.getDescuento() != null && facturaDTO.getDescuento() > 0) {
            totalConDescuento -= facturaDTO.getDescuento();
        }
        factura.setTotal(totalConDescuento);

        // Cálculo del IVA (21%)
        double iva = totalConDescuento * 0.21;
        double totalConIva = totalConDescuento + iva;

        // Establecemos el descuento y el total con IVA en la factura
        factura.setDescuento(facturaDTO.getDescuento());
        factura.setTotal(totalConIva);

        // Fecha de emisión de la factura
        factura.setFechaEmision(LocalDate.from(LocalDateTime.parse(facturaDTO.getFechaEmision())));

        // Guardamos la factura
        facturaRepository.save(factura);

        // Ahora generamos el PDF
        return generarPdfFactura(factura);
    }

    private byte[] generarPdfFactura(Factura factura) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

        PdfFont fontTitulo = PdfFontFactory.createFont("Helvetica-Bold");
        document.add(new Paragraph("Factura de Hotel")
                .setFont(fontTitulo)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER));

        // Información de la factura
        document.add(new Paragraph("ID Factura: " + factura.getId()));
        document.add(new Paragraph("Fecha de emisión: " + factura.getFechaEmision()));

        // Calcular el total sin IVA (se calcula dividiendo el total con IVA entre 1.21)
        double totalSinIva = factura.getTotal() / 1.21;

        // Añadimos los valores al documento PDF
        document.add(new Paragraph("Total sin IVA: " + String.format("%.2f", totalSinIva) + " EUR"));
        document.add(new Paragraph("IVA (21%): " + String.format("%.2f", factura.getTotal() - totalSinIva) + " EUR"));
        document.add(new Paragraph("Total con IVA: " + String.format("%.2f", factura.getTotal()) + " EUR"));

        // Cliente y reserva
        document.add(new Paragraph("Cliente: " + factura.getClienteFactura().getNombre() + " " + factura.getClienteFactura().getNombre()));
        document.add(new Paragraph("Correo electrónico: " + factura.getClienteFactura().getEmail()));
        document.add(new Paragraph("Reserva ID: " + factura.getReservaFactura().getId()));
        document.add(new Paragraph("Fechas: " + factura.getReservaFactura().getFechaInicio() + " - " + factura.getReservaFactura().getFechaFin()));

        // Tabla de productos y servicios
        Table table = new Table(2);
        table.addCell("Concepto");
        table.addCell("Precio");
        table.addCell("Noche de hotel");
        table.addCell("100 EUR"); // Esto debe ser dinámico si es diferente
        if (factura.getDescuento() != null && factura.getDescuento() > 0) {
            table.addCell("Descuento");
            table.addCell("-" + String.format("%.2f", factura.getDescuento()) + " EUR");
        }
        table.addCell("Total");
        table.addCell(String.format("%.2f", factura.getTotal()) + " EUR");

        document.add(table);

        document.close();
        return outputStream.toByteArray();
    }


    // Obtener todas las facturas
    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    // Obtener una factura por su ID
    public Optional<Factura> obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id);
    }

    // Actualizar una factura
    @Transactional
    public Factura actualizarFactura(Long id, FacturaCrearDTO dto) {
        Factura facturaExistente = facturaRepository.findById(id)
                .orElseThrow(() -> new FacturaNoEncontradaException("Factura no encontrada"));

        // Actualizamos la factura con los nuevos datos del DTO

        if(dto.getTotal() !=null)facturaExistente.setTotal(dto.getTotal());
        if(dto.getDescuento() !=null)facturaExistente.setDescuento(dto.getDescuento());


        // Guardamos los cambios
        return facturaRepository.save(facturaExistente);
    }

    // Eliminar una factura
    @Transactional
    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }


}
