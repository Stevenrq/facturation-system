package com.example.facturationsystem.controllers;

import com.example.facturationsystem.models.entities.Customer;
import com.example.facturationsystem.models.entities.Invoice;
import com.example.facturationsystem.models.entities.InvoiceItem;
import com.example.facturationsystem.models.entities.Product;
import com.example.facturationsystem.services.CustomerService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
@Secured("ROLE_ADMIN")
public class InvoiceController {

    @Autowired
    private CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{customerId}")
    public String create(@PathVariable(name = "customerId") Long customerId, Map<String, Object> model,
            RedirectAttributes attributes) {

        Customer customer = customerService.findById(customerId);

        if (customer == null) {
            attributes.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/list";
        }
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);

        model.put("invoice", invoice);
        model.put("title", "Crear factura");
        return "invoice/form";
    }

    /**
     * Método para cargar productos basado en un término de búsqueda.
     *
     * @param term El término de búsqueda utilizado para encontrar productos por
     *             nombre.
     * @return Una lista de productos que coinciden parcialmente con el término de
     *         búsqueda, en formato JSON.
     */
    @GetMapping(value = "/upload-products/{term}", produces = { "application/json" })
    public @ResponseBody List<Product> uploadProducts(@PathVariable String term) {
        return customerService.findByName(term);
    }

    /**
     * Método que maneja la solicitud POST para guardar una factura.
     *
     * @param invoice    el objeto {@link Invoice} a guardar
     * @param result     el objeto {@link BindingResult} que contiene los resultados
     *                   de la validación
     * @param model      el objeto {@link Model} para agregar atributos para la
     *                   vista
     * @param itemId     un array de tipo {@link Long} que contiene los IDs de los
     *                   artículos de la factura
     * @param quantity   un array de tipo {@link Integer} que contiene las
     *                   cantidades de los artículos de la factura
     * @param attributes el objeto {@link RedirectAttributes} para agregar atributos
     *                   para la redirección
     * @param status     el objeto {@link SessionStatus} para indicar el estado de
     *                   la sesión
     * @return la vista a la que se debe redirigir, con mensajes de error o éxito
     *         según corresponda
     */
    @PostMapping("/form")
    public String save(@Valid Invoice invoice, BindingResult result, Model model,
            @RequestParam(name = "item_id[]", required = false) Long[] itemId,
            @RequestParam(name = "quantity[]", required = false) Integer[] quantity, RedirectAttributes attributes,
            SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Crear factura");
            return "invoice/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Crear factura");
            model.addAttribute("error", "La factura debe tener por lo menos un artículo.");
            return "invoice/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Product product = customerService.findProductById(itemId[i]);
            InvoiceItem item = new InvoiceItem();

            item.setQuantity(quantity[i]);
            item.setProduct(product);
            invoice.addInvoiceItem(item);

            logger.info("Id: " + itemId[i].toString() + ", quantity: " + quantity[i].toString());
        }
        customerService.saveInvoice(invoice);
        status.setComplete();
        attributes.addFlashAttribute("success", "Factura creada con éxito");
        return "redirect:/see/" + invoice.getCustomer().getId();
    }

    @GetMapping("/see/{id}")
    public String see(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        Invoice invoice = customerService.fetchInvoiceByIdWithCustomerWithInvoiceItemWithProduct(id);

        if (invoice == null) {
            attributes.addFlashAttribute("error", "La factura no existe");
            return "redirect:/list";
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("title", "Descripción: ".concat(invoice.getDescription()));
        return "invoice/see";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        Invoice invoice = customerService.findInvoiceById(id);

        if (invoice != null) {
            customerService.deleteInvoice(id);
            attributes.addFlashAttribute("success", "Factura eliminada con éxito");
            return "redirect:/see/" + invoice.getCustomer().getId();
        }
        attributes.addFlashAttribute("error", "La factura no existe, no se puedo eliminar");
        return "redirect:/list";
    }
}
