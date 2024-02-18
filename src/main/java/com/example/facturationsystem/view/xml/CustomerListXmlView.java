package com.example.facturationsystem.view.xml;

import com.example.facturationsystem.models.entities.Customer;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Map;

@Component("list.xml")
public class CustomerListXmlView extends MarshallingView {

    @Autowired
    public CustomerListXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    /**
     * Método que renderiza el modelo fusionado en la salida final.
     * Elimina ciertos atributos del modelo y convierte la lista de
     * clientes en un objeto CustomerList para la serialización XML.
     *
     * @param model    el modelo fusionado que se va a renderizar
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @throws Exception si ocurre un error al renderizar la salida
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, @Nonnull HttpServletRequest request,
                                           @Nonnull HttpServletResponse response) throws Exception {

        model.remove("title");
        model.remove("page");

        Page<Customer> customers = (Page<Customer>) model.get("customers");
        model.remove("customers");

        // agrega un objeto CustomerList al modelo para la serialización XML
        model.put("customerList", new CustomerList(customers.getContent()));

        // renderiza la salida final llamando al método renderMergedOutputModel de la superclase
        super.renderMergedOutputModel(model, request, response);
    }
}
