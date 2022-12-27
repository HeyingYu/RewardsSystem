package com.example.rewardssystem.service;

import com.example.rewardssystem.model.Transaction;

import java.util.List;
import java.util.Map;

public interface RewardsService {
    public double calculateTotalRewardPoints(List<Transaction> customerTransactions);
    public double calculateMonthlyRewardPoints(List<Transaction> customerTransactions, String Month);
}
