package com.example.rewardssystem.controller;

import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.service.TransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TransactionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.Test
    public void givenTransactionObject_whenCreateTransaction_thenReturnSavedTransaction() throws Exception {

        // given - precondition or setup
        Transaction transaction = new Transaction(1L, new Customer(1L, "Marco"), 100.0, "Purchase 1", new Date());

        given(transactionsService.saveTransaction(any(Transaction.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer",
                        is(transaction.getCustomer())))
                .andExpect(jsonPath("$.amount",
                        is(transaction.getAmount())))
                .andExpect(jsonPath("$.id",
                        is(transaction.getId())));
    }

    //Junit test for get all Transactions REST API
    @org.junit.jupiter.api.Test
    public void givenListOfTransactions_whenGetAllTransactions_thenReturnTransactionList() throws Exception {
        //given
        List<Transaction> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(new Transaction(1L, new Customer(1L, "Marco"), 100.0, "Purchase 1", new Date()));
        listOfTransactions.add(new Transaction(2L, new Customer(2L, "Luis"), 120.0, "Purchase 2", new Date()));
        given(transactionsService.getAllTransactions()).willReturn(listOfTransactions);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/transactions"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfTransactions.size())));
    }

    // positive scenario - valid Transaction id
    // JUnit test for GET Transaction by id REST API
    @org.junit.jupiter.api.Test
    public void givenTransactionId_whenGetTransactionById_thenReturnTransactionObject() throws Exception {
        // given - precondition or setup
        long transactionId = 1L;
        Transaction transaction = new Transaction(transactionId, new Customer(1L, "Marco"), 100.0, "Purchase 1", new Date());
        given(transactionsService.getTransactionById(transactionId)).willReturn(transaction);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/transactions/{id}", transactionId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(transactionId)));
    }

    // positive scenario - valid Customer id
    // JUnit test for GET all transactions of the given customer by customerId REST API
    @org.junit.jupiter.api.Test
    public void givenCustomerId_whenGetTransactionByCustomerId_thenReturnTransactionList() throws Exception {
        // given - precondition or setup
        long customerId = 1L;
        List<Transaction> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(new Transaction(1L, new Customer(customerId, "Marco"), 100.0, "Purchase 1", new Date()));
        listOfTransactions.add(new Transaction(3L, new Customer(customerId, "Marco"), 120.0, "Purchase 3", new Date()));
        given(transactionsService.getTransactionByCustomerId(customerId)).willReturn(listOfTransactions);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/transactions/customer/{customerId}", customerId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()\"", is(listOfTransactions.size())));
    }
}

