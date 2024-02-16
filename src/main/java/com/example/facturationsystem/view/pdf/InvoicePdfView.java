package com.example.facturationsystem.view.pdf;

import com.example.facturationsystem.models.entities.Invoice;
import com.example.facturationsystem.models.entities.InvoiceItem;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.Locale;
import java.util.Map;

/**
 * Clase que define una vista PDF para la visualización de una factura.
 * Implementa
 * {@link org.springframework.web.servlet.view.document.AbstractPdfView}.
 */
@Component("invoice/see")
public class InvoicePdfView extends AbstractPdfView {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Método para construir el documento PDF que muestra los detalles de una
     * factura.
     *
     * @param model    el modelo de datos para la vista
     * @param document el documento PDF que se está construyendo
     * @param writer   el escritor PDF
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @throws Exception si ocurre algún error al construir el documento PDF
     */
    @SuppressWarnings("null")
    @Override
    protected void buildPdfDocument(Map<String, Object> model, @Nonnull Document document, @Nullable PdfWriter writer,
            @Nonnull HttpServletRequest request, @Nullable HttpServletResponse response) throws Exception {

        Invoice invoice = (Invoice) model.get("invoice");
        PdfPCell cell;
        Locale locale = localeResolver.resolveLocale(request);
        MessageSourceAccessor messages = getMessageSourceAccessor();

        // Tabla para los datos del cliente
        PdfPTable table = new PdfPTable(1);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.invoice.see.customer.data", null, locale)));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        table.addCell(cell);
        table.addCell(invoice.getCustomer().getName() + " " + invoice.getCustomer().getLastName());
        table.addCell(invoice.getCustomer().getEmail());
        table.setSpacingAfter(20);

        // Tabla para los datos de la factura
        PdfPTable table2 = new PdfPTable(1);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.invoice.see.invoice.data", null, locale)));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);
        table2.addCell(cell);
        table2.addCell(messages.getMessage("text.customer.invoice.fol") + ": " + invoice.getId());
        table2.addCell(messages.getMessage("text.customer.invoice.description") + ": " + invoice.getDescription());
        table2.addCell(messages.getMessage("text.customer.invoice.date") + ": " + invoice.getCreatedAt());
        table2.setSpacingAfter(20);

        // Tabla para los elementos de la factura
        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[] { 3.5f, 1, 1, 1 });
        table3.addCell(messages.getMessage("text.invoice.form.item.name"));
        table3.addCell(messages.getMessage("text.invoice.form.item.price"));
        table3.addCell(messages.getMessage("text.invoice.form.item.quantity"));
        table3.addCell(messages.getMessage("text.invoice.form.item.total"));

        for (InvoiceItem item : invoice.getInvoiceItems()) {
            table3.addCell(item.getProduct().getName());
            table3.addCell(item.getProduct().getPrice().toString());

            cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table3.addCell(cell);
            table3.addCell(item.calculateAmount().toString());
        }

        cell = new PdfPCell(new Phrase(messages.getMessage("text.invoice.form.item.total")));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setPadding(5f);
        table3.addCell(cell);
        table3.addCell(invoice.calculateTotal().toString());

        // Agrega las tablas al documento PDF
        document.add(table);
        document.add(table2);
        document.add(table3);
    }
}
