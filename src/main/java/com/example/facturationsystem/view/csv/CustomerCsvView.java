package com.example.facturationsystem.view.csv;

import com.example.facturationsystem.models.entities.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.util.Map;

@Component("list.csv")
public class CustomerCsvView extends AbstractView {

    public CustomerCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"Clientes.csv\"");
        response.setContentType(getContentType());

        Page<Customer> customers = (Page<Customer>) model.get("customers");
        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] header = {"id", "name", "lastName", "email", "createdAt"};
        beanWriter.writeHeader(header);

        for (Customer customer : customers) {
            beanWriter.write(customer, header);
        }
        beanWriter.close();
    }
}
