package com.example.facturationsystem.services;

import com.example.facturationsystem.models.entities.Customer;
import com.example.facturationsystem.models.entities.Invoice;
import com.example.facturationsystem.models.entities.Product;
import com.example.facturationsystem.repositories.InvoiceRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    /**
     * Recupera una página de clientes.
     *
     * @param pageable Objeto Pageable que especifica la paginación y ordenación de
     *                 los resultados.
     * @return Una página de clientes.
     */
    Page<Customer> findAll(Pageable pageable);

    void save(Customer customer);

    Customer findById(Long id);

    /**
     * Recupera un cliente por su ID junto con sus facturas asociadas utilizando la
     * estrategia de LEFT JOIN FETCH en una consulta JPQL personalizada.
     * 
     * @param id el ID del cliente cuyas facturas se desean recuperar junto con el
     *           cliente.
     * @return el cliente recuperado junto con sus facturas asociadas.
     */
    Customer fetchByIdWithInvoices(Long id);

    void delete(Long id);

    /**
     * Busca productos por su nombre, devolviendo una lista de productos cuyos
     * nombres contienen la cadena especificada.
     *
     * @param term El término de búsqueda para encontrar productos por nombre.
     * @return Una lista de productos cuyos nombres coinciden parcialmente con el
     *         término especificado.
     */
    List<Product> findByName(String term);

    void saveInvoice(Invoice invoice);

    Product findProductById(Long id);

    Invoice findInvoiceById(Long id);

    void deleteInvoice(Long id);

    /**
     * Recupera una factura por su ID junto con la información detallada del
     * cliente asociado, los elementos de factura y los productos asociados
     * utilizando la estrategia de JOIN FETCH en una consulta JPQL personalizada.
     * 
     * @param id el ID de la factura que se desea recuperar junto con su información
     *           detallada.
     * @return la factura recuperada junto con la información detallada del cliente
     *         asociado, los elementos de factura y los productos asociados.
     * 
     * @see InvoiceRepository
     */
    Invoice fetchInvoiceByIdWithCustomerWithInvoiceItemWithProduct(Long id);
}
