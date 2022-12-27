package com.example.rewardssystem.model;

import java.time.Month;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class Transaction extends Rewards {
    @jakarta.persistence.Id
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id") //foreign key to customer
    private Customer customer;
    private Double amount;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveDate;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }


    public Transaction() {
        super();
    }
    public Transaction(Long id, Customer customer, double amount, String description, Date date) {
        super();
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.description = description;
        this.saveDate = date;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getDate() {
        return saveDate;
    }

    @Override
    public String getCustomerName() {
        return customer.getName();
    }

    @Override
    public String toString() {
        return String.format("MyTransaction [id=%s, customer=%s, total=%s, description=%s, saveDate=%s]", id, customer,
                amount, description, saveDate);
    }
}

