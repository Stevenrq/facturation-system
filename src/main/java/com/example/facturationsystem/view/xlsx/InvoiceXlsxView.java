package com.example.facturationsystem.view.xlsx;

import com.example.facturationsystem.models.entities.Invoice;
import com.example.facturationsystem.models.entities.InvoiceItem;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.Map;

@Component("invoice/see.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, @Nullable HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"Factura.xlsx\"");
        Invoice invoice = (Invoice) model.get("invoice");
        Sheet sheet = workbook.createSheet("Factura");
        Row row;
        Cell cell;
        MessageSourceAccessor messages = getMessageSourceAccessor();


        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue(messages.getMessage("text.invoice.see.customer.data"));

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(invoice.getCustomer().getName() + " " + invoice.getCustomer().getLastName());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(invoice.getCustomer().getEmail());

        sheet.createRow(4).createCell(0).setCellValue(messages.getMessage("text.invoice.see.invoice.data"));
        sheet.createRow(5).createCell(0).setCellValue(messages.getMessage("text.customer.invoice.fol") + ": " + invoice.getId());
        sheet.createRow(6).createCell(0).setCellValue(messages.getMessage("text.customer.invoice.description") + ": " + invoice.getDescription());
        sheet.createRow(7).createCell(0).setCellValue(messages.getMessage("text.customer.invoice.date") + ": " + invoice.getCreatedAt());

        CellStyle tHeaderStyle = workbook.createCellStyle();
        tHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        tHeaderStyle.setBorderTop(BorderStyle.MEDIUM);
        tHeaderStyle.setBorderRight(BorderStyle.MEDIUM);
        tHeaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        tHeaderStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        tHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tBodyStyle = workbook.createCellStyle();
        tBodyStyle.setBorderBottom(BorderStyle.THIN);
        tBodyStyle.setBorderTop(BorderStyle.THIN);
        tBodyStyle.setBorderRight(BorderStyle.THIN);
        tBodyStyle.setBorderLeft(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(messages.getMessage("text.invoice.form.item.name"));
        header.createCell(1).setCellValue(messages.getMessage("text.invoice.form.item.price"));
        header.createCell(2).setCellValue(messages.getMessage("text.invoice.form.item.quantity"));
        header.createCell(3).setCellValue(messages.getMessage("text.invoice.form.item.total"));

        header.getCell(0).setCellStyle(tHeaderStyle);
        header.getCell(1).setCellStyle(tHeaderStyle);
        header.getCell(2).setCellStyle(tHeaderStyle);
        header.getCell(3).setCellStyle(tHeaderStyle);

        int rowNum = 10;
        for (InvoiceItem item: invoice.getInvoiceItems()) {
            Row itemRow = sheet.createRow(rowNum++);
            cell = itemRow.createCell(0);
            cell.setCellValue(item.getProduct().getName());
            cell.setCellStyle(tBodyStyle);

            cell = itemRow.createCell(1);
            cell.setCellValue(item.getProduct().getPrice());
            cell.setCellStyle(tBodyStyle);

            cell = itemRow.createCell(2);
            cell.setCellValue(item.getQuantity());
            cell.setCellStyle(tBodyStyle);

            cell = itemRow.createCell(3);
            cell.setCellValue(item.calculateAmount());
            cell.setCellStyle(tBodyStyle);
        }

        Row totalRow = sheet.createRow(rowNum);
        cell = totalRow.createCell(2);
        cell.setCellValue(messages.getMessage("text.invoice.form.item.total"));
        cell.setCellStyle(tBodyStyle);

        cell = totalRow.createCell(3);
        cell.setCellValue(invoice.calculateTotal());
        cell.setCellStyle(tBodyStyle);
    }
}
