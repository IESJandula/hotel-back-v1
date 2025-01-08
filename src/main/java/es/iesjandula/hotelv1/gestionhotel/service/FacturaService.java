package es.iesjandula.hotelv1.gestionhotel.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import es.iesjandula.hotelv1.gestionhotel.exception.Cliente.ClienteNoEncontradoException;
import es.iesjandula.hotelv1.gestionhotel.exception.Reserva.ResevaNoEncontradaException;
import es.iesjandula.hotelv1.gestionhotel.model.Cliente;
import es.iesjandula.hotelv1.gestionhotel.model.DTO.Factura.FacturaCrearDTO;
import es.iesjandula.hotelv1.gestionhotel.model.Factura;
import es.iesjandula.hotelv1.gestionhotel.model.Reserva;
import es.iesjandula.hotelv1.gestionhotel.repository.ClienteRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.FacturaRepository;
import es.iesjandula.hotelv1.gestionhotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

        // Si tienes un IVA que calcular, lo aplicamos aquí también
        double iva = totalConDescuento * 0.21; // Ejemplo de IVA del 21%
        double totalConIva = totalConDescuento + iva;
        factura.setTotal(totalConIva);

        // Establecemos el descuento en la factura
        factura.setDescuento(facturaDTO.getDescuento());

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
        document.add(new Paragraph("Total sin IVA: " + String.format("%.2f", factura.getTotal() / 1.21) + " EUR"));
        document.add(new Paragraph("IVA (21%): " + String.format("%.2f", factura.getTotal() - factura.getTotal() / 1.21) + " EUR"));
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

}
