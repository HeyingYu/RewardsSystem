package com.example.rewardssystem.controller;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.service.RewardsService;
import com.example.rewardssystem.service.TransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RewardsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @MockBean
    private TransactionsService transactionsService;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.Test
    public void givenCustomerId_whenCalculateTotalRewardPoints_thenReturnTotalRewardPoints() throws Exception {
        // Set up test data
        long customerId = 1L;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.MONTH, 9);
        Date date1 = calendar1.getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.MONTH, 8);
        Date date2 = calendar1.getTime();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.MONTH, 7);
        Date date3 = calendar1.getTime();
        Transaction transaction = new Transaction(1L, new Customer(customerId, "Marco"), 50.0, "Purchase 1", date1);
        Transaction transaction1 = new Transaction(2L, new Customer(customerId, "Marco"), 100.0, "Purchase 2", date2);
        Transaction transaction2 = new Transaction(3L, new Customer(customerId, "Marco"), 120.0, "Purchase 3", date3);
        List<Transaction> transactionList = new ArrayList<>(Arrays.asList(transaction, transaction1, transaction2));
        // Set up mock behavior

        when(transactionsService.getTransactionByCustomerId(customerId)).thenReturn(transactionList);

        // Perform the test
        ResultActions response = mockMvc.perform(get("/api/rewards/calculateRewards/{customerId}/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerId)));

        // Verify the results
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalRewards",
                        is(390)));
    }

    @org.junit.jupiter.api.Test
    public void givenCustomerIdAndMonth_whenCalculateMonthlyRewardPoints_thenReturnMonthlyRewardPoints() throws Exception {
        // Set up test data
        long customerId = 1L;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.MONTH, 9);
        Date date1 = calendar1.getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.MONTH, 8);
        Date date2 = calendar1.getTime();
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.MONTH, 7);
        Date date3 = calendar1.getTime();
        Transaction transaction = new Transaction(1L, new Customer(customerId, "Marco"), 50.0, "Purchase 1", date1);
        Transaction transaction1 = new Transaction(2L, new Customer(customerId, "Marco"), 100.0, "Purchase 2", date2);
        Transaction transaction2 = new Transaction(3L, new Customer(customerId, "Marco"), 120.0, "Purchase 3", date3);
        List<Transaction> transactionList = new ArrayList<>(Arrays.asList(transaction, transaction1, transaction2));
        String month1 = "October", month2 = "September", month3 = "August";
        // Set up mock behavior

        when(transactionsService.getTransactionByCustomerId(customerId)).thenReturn(transactionList);

        // Perform the test
        ResultActions response1 = mockMvc.perform(get("/api/rewards/calculateRewards/{customerId}/{month}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerId))
                .content(objectMapper.writeValueAsString(month1)));

        // Verify the results
        response1.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.monthlyRewards.month",
                        is(month1)))
                .andExpect(jsonPath("$.monthlyRewards.month.rewards",
                        is(0)));

        // Perform the test
        ResultActions response2 = mockMvc.perform(get("/api/rewards/calculateRewards/{customerId}/{month}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerId))
                .content(objectMapper.writeValueAsString(month2)));

        // Verify the results
        response2.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.monthlyRewards.month",
                        is(month2)))
                .andExpect(jsonPath("$.monthlyRewards.month.rewards",
                        is(50)));

        // Perform the test
        ResultActions response3 = mockMvc.perform(get("/api/rewards/calculateRewards/{customerId}/{month}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerId))
                .content(objectMapper.writeValueAsString(month3)));

        // Verify the results
        response3.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.monthlyRewards.month",
                        is(month3)))
                .andExpect(jsonPath("$.monthlyRewards.month.rewards",
                        is(90)));
    }
}

