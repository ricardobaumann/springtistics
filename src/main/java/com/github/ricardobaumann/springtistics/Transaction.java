package com.github.ricardobaumann.springtistics;

import javax.validation.constraints.NotNull;

public class Transaction {

    @NotNull
    private Double amount;

    @NotNull
    private Long timestamp = System.currentTimeMillis();

    Transaction(@NotNull Double amount, @NotNull Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
