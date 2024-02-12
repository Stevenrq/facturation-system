package com.example.facturationsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.facturationsystem.models.entities.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
