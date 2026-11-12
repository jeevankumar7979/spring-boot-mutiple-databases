package com.jeevankumar.app.mysql.controller;

import com.jeevankumar.app.mysql.model.Customer;
import com.jeevankumar.app.mysql.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer name must not be blank");
        }

        Customer customer = customerService.createCustomer(request.name().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    public record CreateCustomerRequest(String name) {
    }
}
