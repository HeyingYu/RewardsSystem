package com.example.rewardssystem.service;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class RewardsServiceTests {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsService rewardsService;

    private List<Transaction> transactionList;

    private Long customerId;

    @BeforeEach
    public void setUp() {
        customerId = 1L;
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
    }

    @DisplayName("JUnit test for calculateTotalRewardPoints method")
    @Test
    public void givenTransactionList_whenCalculateTotalRewardPoints_thenReturnTotalRewardPoints() {
        // given - precondition or setup
        given(transactionRepository.findByCustomerId(customerId)).willReturn(transactionList);

        // when -  action or the behaviour that we are going test
        Double totalRewardPoints = rewardsService.calculateTotalRewardPoints(transactionList);

        // then - verify the output
        assertThat(totalRewardPoints).isEqualTo(390);
    }

    @DisplayName("JUnit test for calculateMonthlyRewardPoint method")
    @Test
    public void givenTransactionList_whenCalculateMonthlyRewardPoints_thenReturnMonthlyRewardPoints() {
        // given - precondition or setup
        given(transactionRepository.findByCustomerId(customerId)).willReturn(transactionList);

        // when -  action or the behaviour that we are going test
        Double rewardPointsOct = rewardsService.calculateMonthlyRewardPoints(transactionList, "October");

        // then - verify the output
        assertThat(rewardPointsOct).isEqualTo(0);

        Double rewardPointsSep = rewardsService.calculateMonthlyRewardPoints(transactionList, "September");

        assertThat(rewardPointsSep).isEqualTo(50);

        Double rewardPointsAug = rewardsService.calculateMonthlyRewardPoints(transactionList, "August");

        assertThat(rewardPointsSep).isEqualTo(90);
    }

}

