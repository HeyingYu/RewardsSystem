package com.example.rewardssystem.service.impl;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.repository.TransactionRepository;
import com.example.rewardssystem.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionsServiceImpl(TransactionRepository transactionRepository) {this.transactionRepository = transactionRepository;}

    @Override
    public List<Transaction> getTransactionByCustomerId(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    public Transaction getTransactionById(Long customerId) {
        return transactionRepository.findById(customerId).orElse(null);
    }
    @Override
    public Transaction saveTransaction(Transaction transaction) {

        Optional<Transaction> savedTransaction = transactionRepository.findById(transaction.getId());
        if(savedTransaction.isPresent()){
            throw new ResourceNotFoundException("Transaction already exist with given id:" + transaction.getId());
        }
        return transactionRepository.save(transaction);
    }
}

