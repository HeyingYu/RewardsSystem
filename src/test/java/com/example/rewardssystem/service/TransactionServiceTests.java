package com.example.rewardssystem.service;

import com.example.rewardssystem.model.Customer;
import com.example.rewardssystem.model.Transaction;
import com.example.rewardssystem.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class TransactionServiceTests {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionsService transactionsService;

    private Transaction transaction;

    private Long customerId;

    @BeforeEach
    public void setUp() {
        customerId = 1L;
        Transaction transaction = new Transaction(1L, new Customer(customerId, "Marco"), 100.0, "Purchase 1", new Date());
    }

    @DisplayName("JUnit test for saveTransaction method")
    @Test
    public void givenTransactionObject_whenSaveTransaction_thenReturnTransactionObject() {
        // given - precondition or setup
        given(transactionRepository.findById(transaction.getId()))
                .willReturn(Optional.empty());

        given(transactionRepository.save(transaction)).willReturn(transaction);

        // when -  action or the behaviour that we are going test
        Transaction saveTransaction = transactionsService.saveTransaction(transaction);

        // then - verify the output
        assertThat(saveTransaction).isNotNull();
    }

    @DisplayName("JUnit test for saveTransaction method which throws exception")
    @Test
    public void givenExistingId_whenSaveTransaction_thenThrowsException() {
        // given - precondition or setup
        given(transactionRepository.findById(transaction.getId()))
                .willReturn(Optional.of(transaction));

        // when -  action or the behaviour that we are going test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionsService.saveTransaction(transaction);
        });

        // then
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @DisplayName("JUnit test for getAllTransactions method (negative scenario)")
    @Test
    public void givenEmptyTransactionsList_whenGetAllTransactions_thenReturnEmptyTransactionsList() {
        // given - precondition or setup
        given(transactionRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Transaction> transactionList = transactionsService.getAllTransactions();

        // then - verify the output
        assertThat(transactionList).isEmpty();
        assertThat(transactionList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getAllTransactions method")
    @Test
    public void givenTransactionList_whenGetTransactionAll_thenReturnTransactionList() {
        //given
        Transaction transaction1 = new Transaction(2L, new Customer(2L, "Luis"), 120.0, "Purchase 2", new Date());
        given(transactionRepository.findAll()).willReturn(List.of(transaction, transaction1));
        //given
        List<Transaction> transactionList = transactionsService.getAllTransactions();
        //then
        assertThat(transactionList).isNotNull();
        assertThat(transactionList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getTransactionById method")
    @Test
    public void givenTransactionId_whenGetTransactionById_thenReturnTransactionObject() {
        // given
        given(transactionRepository.findById(1L)).willReturn(Optional.of(transaction));

        // when
        Transaction savedTransaction = transactionsService.getTransactionById(transaction.getId());

        // then
        assertThat(savedTransaction).isNotNull();
    }

    @DisplayName("Junit test for getTransactionByCustomerId method")
    @Test
    public void givenCustomerId_whenGetTransactionByCustomerId_thenReturnTransactionList() {
        //given
        Transaction transaction2 = new Transaction(3L, new Customer(customerId, "Marco"), 150.0, "Purchase 3", new Date());
        given(transactionRepository.findByCustomerId(1L)).willReturn(List.of(transaction, transaction2));

        //when
        List<Transaction> transactionList = transactionsService.getTransactionByCustomerId(1L);

        //then
        assertThat(transactionList).isNotNull();
        assertThat(transactionList.size()).isEqualTo(2);
        assertThat(transactionList.get(0).getCustomer().getId()).isEqualTo(customerId);
        assertThat(transactionList.get(1).getCustomer().getId()).isEqualTo(customerId);
    }
}

