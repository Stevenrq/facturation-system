package com.example.facturationsystem.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.facturationsystem.models.entities.Product;
import com.example.facturationsystem.repositories.ProductRepository;
import com.example.facturationsystem.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
