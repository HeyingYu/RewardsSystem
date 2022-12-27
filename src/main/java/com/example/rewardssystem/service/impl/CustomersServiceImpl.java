package com.example.rewardssystem.service.impl;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.repository.CustomerRepository;
import com.example.rewardssystem.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersServiceImpl implements CustomersService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomersServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public Customer saveCustomer(Customer employee) {

        Optional<Customer> savedCustomer = customerRepository.findById(employee.getId());
        if(savedCustomer.isPresent()){
            throw new ResourceNotFoundException("Employee already exist with given name:" + employee.getName());
        }
        return customerRepository.save(employee);
    }
}

