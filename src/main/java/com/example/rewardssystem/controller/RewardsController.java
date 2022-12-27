package com.example.rewardssystem.controller;

import java.util.*;

import com.example.service.springbootrewards.model.Transaction;
import com.example.service.springbootrewards.service.RewardsService;
import com.example.service.springbootrewards.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {
    private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private RewardsService rewardService;

    @GetMapping("/calculateRewards/{customerId}/{month}")
    public ResponseEntity<Map<String, Double>> calculateMonthlyRewards(@PathVariable("customerId") String customerId, @PathVariable("month") String month) {
        Map<String, Double> results = new HashMap<>();

        //retrieves the transactions for the specified customer
        List<Transaction> customerTransactions = transactionsService.getTransactionByCustomerId(Long.parseLong(customerId));
        if (customerTransactions == null) {
            return ResponseEntity.notFound().build();
        }

        Double rewardsByMonth = rewardService.calculateMonthlyRewardPoints(customerTransactions, month);

        if(rewardsByMonth <= 0) {
            return ResponseEntity.notFound().build();
        }
        // Log the results
        logger.info("Calculated rewards for customer {}: {}", customerId, rewardsByMonth);
        results.put(customerId, rewardsByMonth);

        return ResponseEntity.ok(results);
    }

    /**
     * accepts a customer ID as a path variable
     * If the customer is not found, returns a ResponseEntity with a 404 Not Found status
     * Otherwise, it returns a ResponseEntity with a 200 OK status and the results in the body
     */
    @GetMapping("/calculateRewards/{customerId}/total")
    public ResponseEntity<Map<String, Double>> calculateTotalRewards(@PathVariable("customerId") String customerId) {
        Map<String, Double> results = new HashMap<>();

        //retrieves the transactions for the specified customer
        List<Transaction> customerTransactions = transactionsService.getTransactionByCustomerId(Long.parseLong(customerId));
        if (customerTransactions == null) {
            return ResponseEntity.notFound().build();
        }

        double totalRewards = rewardService.calculateTotalRewardPoints(customerTransactions);

        // Log the results
        logger.info("Calculated rewards for customer {}: {}", customerId, totalRewards);
        results.put(customerId, totalRewards);

        return ResponseEntity.ok(results);
    }

}

