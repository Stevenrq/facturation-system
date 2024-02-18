package com.example.facturationsystem.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "invoices_items")
public class InvoiceItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    /**
     * La anotación {@code @JsonIgnoreProperties} se utiliza para indicar las propiedades de la instancia de Hibernate a
     * ignorar durante la serialización de objetos JSON, evitando así problemas con la inicialización perezosa
     * ({@code hibernateLazyInitializer}) y el controlador ({@code handler}).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double calculateAmount() {
        return quantity.doubleValue() * product.getPrice();
    }
}
