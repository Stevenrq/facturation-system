package com.example.facturationsystem.controllers;

import com.example.facturationsystem.services.CustomerService;
import com.example.facturationsystem.view.xml.CustomerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public CustomerList list() {
        return new CustomerList(customerService.findAll());
    }
}
