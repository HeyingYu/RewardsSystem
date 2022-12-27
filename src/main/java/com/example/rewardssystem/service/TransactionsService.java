package com.example.rewardssystem.service;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;

import java.util.List;


public interface TransactionsService {
    public List<Transaction> getAllTransactions();
    public Transaction getTransactionById(Long transactionId);

    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getTransactionByCustomerId(Long customerId);
}
