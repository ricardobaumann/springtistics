package com.github.ricardobaumann.springtistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceCreateTransactionTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void shouldReturnFalseOnOldTimestamp() {
        //Given
        Long sixtyOneSecsAgo = LocalDateTime.now().minusSeconds(61)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Transaction transaction = new Transaction(10.0, sixtyOneSecsAgo);

        //When //Then
        assertThat(transactionService.createTransaction(transaction)).isFalse();
        verify(transactionRepo, never()).save(any());
    }

    @Test
    public void shouldReturnTrueOnRecentTimestamp() {
        //Given
        Long sixtySecsAgo = LocalDateTime.now().minusSeconds(59)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Transaction transaction = new Transaction(10.0, sixtySecsAgo);
        doNothing().when(transactionRepo).save(transaction);

        //When //Then
        assertThat(transactionService.createTransaction(transaction)).isTrue();
    }

}