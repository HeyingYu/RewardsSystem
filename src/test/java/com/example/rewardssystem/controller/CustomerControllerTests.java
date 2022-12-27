package com.example.rewardssystem.controller;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.service.CustomersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CustomerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomersService customersService;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.Test
    public void givenCustomerObject_whenCreateCustomer_thenReturnSavedCustomer() throws Exception{

        // given - precondition or setup
        Customer customer = new Customer(1L, "Marco");

        given(customersService.saveCustomer(any(Customer.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(customer.getName())))
                .andExpect(jsonPath("$.id",
                        is(customer.getId())));
    }

    //Junit test for get all customers REST API
    @org.junit.jupiter.api.Test
    public void givenListOfCustomers_whenGetAllCustomers_thenReturnCustomerList() throws Exception{
        //given
        List<Customer> listOfCustomers = new ArrayList<>();
        listOfCustomers.add(new Customer(1L, "Marco"));
        listOfCustomers.add(new Customer(2L, "Luis"));
        given(customersService.getAllCustomers()).willReturn(listOfCustomers);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customers"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCustomers.size())));
    }

    // positive scenario - valid customer id
    // JUnit test for GET customer by id REST API
    @org.junit.jupiter.api.Test
    public void givenCustomerId_whenGetCustomerById_thenReturnCustomerObject() throws Exception{
        // given - precondition or setup
        long customerId = 1L;
        Customer customer = new Customer(1L, "Marco");
        given(customersService.getCustomerById(customerId)).willReturn(customer);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/customers/{id}", customerId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }
}

