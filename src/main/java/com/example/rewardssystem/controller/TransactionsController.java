package com.example.rewardssystem.controller;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody Transaction transation){
        return transactionsService.saveTransaction(transation);
    }

    @GetMapping
    public List<Transaction> findTransactionsAll() {
        return transactionsService.getAllTransactions();
    }

    @GetMapping("{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        Transaction transaction = transactionsService.getTransactionById(id);
        if (transaction == null) return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public List<Transaction> getTransactionsByCustomerId(@PathVariable Long customerId) {
        return transactionsService.getTransactionByCustomerId(customerId);
    }
}

