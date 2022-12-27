package com.example.rewardssystem.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class Rewards {

    @JsonInclude  //include in Jackson serialization
    protected Long points;

    public abstract double getAmount();


    public abstract Date getDate();

    public abstract String getCustomerName();
}

