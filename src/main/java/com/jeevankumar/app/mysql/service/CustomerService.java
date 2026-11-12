package com.jeevankumar.app.mysql.service;

import com.jeevankumar.app.mysql.model.Customer;
import com.jeevankumar.app.mysql.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional("mysqlTransactionManager")
    public Customer createCustomer(String name) {
        return customerRepository.save(new Customer(name));
    }
}
