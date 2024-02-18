package com.example.facturationsystem.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    private String observation;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> invoiceItems;

    public Invoice() {
        this.invoiceItems = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createAt) {
        this.createdAt = createAt;
    }

    /**
     * Obtiene el cliente asociado a la factura.
     *
     * @return el cliente asociado a la factura.
     * @apiNote Este método está marcado como @XmlTransient, lo que significa que no será serializado/deserializado por
     * los procesos de marshalling/unmarshalling de XML.
     */
    @XmlTransient
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void addInvoiceItem(InvoiceItem item) {
        this.invoiceItems.add(item);
    }

    public Double calculateTotal() {
        Double total = 0.0;

        for (InvoiceItem invoiceItem : invoiceItems) {
            total += invoiceItem.calculateAmount();
        }
        return total;
    }
}
