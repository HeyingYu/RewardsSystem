package com.example.rewardssystem.repository;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByCustomerId(Long customerId);
}
