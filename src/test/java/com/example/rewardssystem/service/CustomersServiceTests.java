package com.example.rewardssystem.service;

import com.example.rewardssystem.model.Customer;

import com.example.rewardssystem.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import static org.mockito.Matchers.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomersServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomersService customersService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1L, "Marco");
    }

    @DisplayName("JUnit test for saveCustomer method")
    @Test
    public void givenCustomerObject_whenSaveCustomer_thenReturnCustomerObject() {
        // given - precondition or setup
        given(customerRepository.findById(customer.getId()))
                .willReturn(Optional.empty());

        given(customerRepository.save(customer)).willReturn(customer);

        // when -  action or the behaviour that we are going test
        Customer savedCustomer = customersService.saveCustomer(customer);

        // then - verify the output
        assertThat(savedCustomer).isNotNull();
    }

    @DisplayName("JUnit test for saveCustomer method which throws exception")
    @Test
    public void givenExistingId_whenSaveCustomer_thenThrowsException() {
        // given - precondition or setup
        given(customerRepository.findById(customer.getId()))
                .willReturn(Optional.of(customer));

        // when -  action or the behaviour that we are going test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            customersService.saveCustomer(customer);
        });

        // then
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @DisplayName("JUnit test for getAllCustomers method (negative scenario)")
    @Test
    public void givenEmptyCustomersList_whenGetAllCustomers_thenReturnEmptyCustomersList() {
        // given - precondition or setup
        Customer customer1 = new Customer(2L, "Luis");

        given(customerRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Customer> customerList = customersService.getAllCustomers();

        // then - verify the output
        assertThat(customerList).isEmpty();
        assertThat(customerList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getAllCustomer method")
    @Test
    public void givenCustomerList_whenGetCustomerAll_thenReturnCustomerList() {
        //given
        Customer customer1 = new Customer(2L, "Luis");
        given(customerRepository.findAll()).willReturn(List.of(customer, customer1));
        //given
        List<Customer> customerList = customersService.getAllCustomers();
        //then
        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getCustomerById method")
    @Test
    public void givenCustomerId_whenGetCustomerById_thenReturnCustomerObject() {
        // given
        given(customerRepository.findById(1L)).willReturn(Optional.of(customer));

        // when
        Customer savedCustomer = customersService.getCustomerById(customer.getId());

        // then
        assertThat(savedCustomer).isNotNull();
    }

}

