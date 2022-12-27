package com.example.rewardssystem.service;

import com.example.rewardssystem.model.Customer;

import java.util.List;

public interface CustomersService {
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(Long customerId);

    Customer saveCustomer(Customer customer);
}
