package com.example.facturationsystem.view.xml;

import com.example.facturationsystem.models.entities.Customer;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * Clase que representa una lista de clientes para la serialización XML.
 * Se utiliza como envoltorio para una lista de objetos Customer.
 */
@XmlRootElement(name = "customers")
public class CustomerList {

    @XmlElement(name = "customer")
    public List<Customer> customers;

    public CustomerList() {
    }

    public CustomerList(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
