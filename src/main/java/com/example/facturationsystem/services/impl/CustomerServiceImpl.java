package com.example.facturationsystem.services.impl;

import com.example.facturationsystem.models.entities.Customer;
import com.example.facturationsystem.models.entities.Invoice;
import com.example.facturationsystem.models.entities.Product;
import com.example.facturationsystem.repositories.CustomerRepository;
import com.example.facturationsystem.repositories.InvoiceRepository;
import com.example.facturationsystem.repositories.ProductRepository;
import com.example.facturationsystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer fetchByIdWithInvoices(Long id) {
        return customerRepository.fetchByIdWithInvoices(id);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByName(String term) {
        return productRepository.findByNameLikeIgnoreCase("%" + term + "%");
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice fetchInvoiceByIdWithCustomerWithInvoiceItemWithProduct(Long id) {
        return invoiceRepository.fetchByIdWithCustomerWithInvoiceItemWithProduct(id);
    }
}
