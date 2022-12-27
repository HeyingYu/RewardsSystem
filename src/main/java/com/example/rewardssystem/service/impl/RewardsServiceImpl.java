package com.example.rewardssystem.service.impl;

import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.repository.TransactionRepository;
import com.example.rewardssystem.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardsServiceImpl implements RewardsService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public double calculateTotalRewardPoints(List<Transaction> customerTransactions) {
        double totalRewards = 0;

        for (Transaction transaction : customerTransactions) {
            double transactionRewards = calculateRewardPointsPerTransaction(transaction);
            totalRewards += transactionRewards;
        }

        return totalRewards;
    }

    @Override
    public double calculateMonthlyRewardPoints(List<Transaction> customerTransactions, String month) {
        double rewardsByMonth = 0;

        for (Transaction transaction : customerTransactions) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(transaction.getDate());
            int monthValue = calendar.get(Calendar.MONTH);
            String monthName = new DateFormatSymbols().getMonths()[monthValue];

            if(monthName.equals(month)) {
                rewardsByMonth += calculateRewardPointsPerTransaction(transaction);
            }
        }
        return rewardsByMonth;
    }

    private double calculateRewardPointsPerTransaction(Transaction transaction) {
        double points = 0;
        double amount = transaction.getAmount();

        if (amount > 50 && amount <= 100) {
            points += (amount - 50) * 1;

        }

        if (amount > 100) {
            points += 50;  //1 point for every dollar spent over $50
            points += (amount - 100) * 2;  //2 points for every dollar spent over $100
        }

        return points;
    }
}

