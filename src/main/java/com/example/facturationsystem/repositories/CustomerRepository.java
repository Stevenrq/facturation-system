package com.example.facturationsystem.repositories;

import com.example.facturationsystem.models.entities.Customer;
import com.example.facturationsystem.models.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Recupera un cliente por su ID junto con sus facturas asociadas utilizando la
     * estrategia de LEFT JOIN FETCH en una consulta JPQL personalizada.
     * <p>
     * Esta consulta optimizada utiliza LEFT JOIN FETCH para recuperar el cliente
     * por su ID y cargar de manera eficiente sus facturas asociadas en una sola
     * consulta.
     * La optimización se logra mediante la inclusión de un left join fetch en la
     * consulta JPQL para evitar consultas adicionales de forma separada.
     * </p>
     * <p>
     * La anotación @Query especifica la consulta JPQL personalizada donde:
     * - 'c' representa la entidad Customer.
     * - 'i' representa la entidad Invoice asociada.
     * </p>
     * <p>
     * Esta consulta utiliza left join fetch para cargar de forma eficiente las
     * facturas asociadas al cliente.
     * </p>
     *
     * @param id el ID del cliente cuyas facturas se desean recuperar junto con el
     *           cliente.
     * @return el cliente recuperado junto con sus facturas asociadas.
     * @see Customer
     * @see Invoice
     */
    @Query("select c from Customer c left join fetch c.invoices i where c.id=?1")
    Customer fetchByIdWithInvoices(Long id);
}
