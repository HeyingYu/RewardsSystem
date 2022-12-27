package com.example.rewardssystem.model;

import java.util.Set;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class Customer {
    @jakarta.persistence.Id
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy="customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
    @JsonInclude
    @Transient
    private Long rewardPoints;
    @JsonInclude
    @Transient
    private Double totalPurchases;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer() {
        super();
    }
    public Customer(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}

