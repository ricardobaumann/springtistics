package com.github.ricardobaumann.springtistics;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class TransactionRepoTest {

    private final List<Transaction> transactionList = new ArrayList<>();

    private final TransactionRepo transactionRepo = new TransactionRepo(transactionList);

    private final Transaction testTransaction = new Transaction(10.0, System.currentTimeMillis());

    @Test
    public void shouldSaveSuccessfully() {

        //Given //When
        transactionRepo.save(testTransaction);

        //Then
        assertThat(transactionList)
                .hasSize(1)
                .extracting("amount","timestamp")
                .contains(Tuple.tuple(testTransaction.getAmount(),testTransaction.getTimestamp()));
    }

    @Test
    public void shouldFindAllTransactions() {
        //Given
        assertThat(transactionRepo.findAll()).isEmpty();
        transactionList.add(testTransaction);

        //When //Then
        assertThat(transactionRepo.findAll())
                .hasSize(1)
                .extracting("amount","timestamp")
                .contains(Tuple.tuple(testTransaction.getAmount(),testTransaction.getTimestamp()));

    }

    @Test
    public void shouldDeleteByTimestampLesserThan() {
        //Given
        Transaction oldTransaction = new Transaction(23.0, LocalDateTime.now().minusSeconds(100)
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());

        transactionList.add(testTransaction);
        transactionList.add(testTransaction);
        transactionList.add(oldTransaction);

        //When
        transactionRepo.deleteByTimestampLesserThan(oldTransaction.getTimestamp()+1);

        //Then
        assertThat(transactionList)
                .hasSize(2)
                .extracting("amount","timestamp")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(testTransaction.getAmount(),testTransaction.getTimestamp()),
                        Tuple.tuple(testTransaction.getAmount(),testTransaction.getTimestamp()));

    }
}