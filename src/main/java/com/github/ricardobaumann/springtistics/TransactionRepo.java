package com.github.ricardobaumann.springtistics;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepo {

    private final List<Transaction> transactions;

    public TransactionRepo(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    void save(Transaction transaction) {
        transactions.add(transaction);
    }

    List<Transaction> findAll() {
        return transactions;
    }

    void deleteByTimestampLesserThan(long timestamp) {
        synchronized (transactions) {
            transactions.removeIf(transaction -> transaction.getTimestamp()< timestamp);
        }
    }
}
