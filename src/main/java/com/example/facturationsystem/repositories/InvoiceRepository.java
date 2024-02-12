package com.example.facturationsystem.repositories;

import com.example.facturationsystem.models.entities.Customer;
import com.example.facturationsystem.models.entities.InvoiceItem;
import com.example.facturationsystem.models.entities.Product;
import com.example.facturationsystem.models.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Recupera una factura por su ID junto con la información detallada del cliente
     * asociado, los elementos de factura y los productos asociados utilizando la
     * estrategia de JOIN FETCH en una consulta JPQL personalizada.
     * <p>
     * Esta consulta optimizada utiliza JOIN FETCH para recuperar la factura por su
     * ID y cargar de manera eficiente la información asociada del cliente, los
     * elementos de factura y los productos asociados en una sola consulta.
     * La optimización se logra mediante la inclusión de joins fetch en la consulta
     * JPQL para evitar consultas adicionales de forma separada.
     * </p>
     * <p>
     * La anotación @Query especifica la consulta JPQL personalizada donde:
     * - 'i' representa la entidad Invoice.
     * - 'c' representa la entidad Customer asociada.
     * - 'ii' representa la entidad InvoiceItem asociada.
     * </p>
     * <p>
     * Esta consulta utiliza los joins fetch para cargar de forma eficiente la
     * información relacionada con la factura, el cliente, los elementos de factura
     * y los productos asociados.
     * </p>
     *
     * @param id el ID de la factura que se desea recuperar junto con su información
     *           detallada.
     * @return la factura recuperada junto con la información detallada del cliente
     *         asociado, los elementos de factura y los productos asociados.
     * @see Invoice
     * @see Customer
     * @see InvoiceItem
     * @see Product
     */
    @Query("select i from Invoice i join fetch i.customer c join fetch i.invoiceItems ii join fetch ii.product where i.id=?1")
    Invoice fetchByIdWithCustomerWithInvoiceItemWithProduct(Long id);
}
