package com.example.facturationsystem.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.facturationsystem.models.entities.Product;

public interface ProductService {

    List<Product> findAll();

    void save(Product product);

    Product findById(Long id);

    void delete(Long id);

    /**
     * Recupera una página de productos.
     *
     * @param pageable Objeto Pageable que especifica la paginación y ordenación de
     *                 los resultados.
     * @return Una página de productos.
     */
    Page<Product> findAll(Pageable pageable);
}
