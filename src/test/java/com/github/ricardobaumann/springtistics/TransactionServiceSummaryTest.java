package com.github.ricardobaumann.springtistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceSummaryTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void shouldReturnEmptySummaryIfNoData() {
        //Given
        when(transactionRepo.findAll()).thenReturn(Collections.emptyList());

        //When
        transactionService.cleanupAndSummarize();

        // Then
        assertThat(transactionService.getCurrentSummary())
                .hasFieldOrPropertyWithValue("min", 0.0)
                .hasFieldOrPropertyWithValue("max", 0.0)
                .hasFieldOrPropertyWithValue("sum", 0.0)
                .hasFieldOrPropertyWithValue("avg", 0.0)
                .hasFieldOrPropertyWithValue("count", 0);
    }

    @Test
    public void shouldReturnFilledSummaryWithAvailableData() {
        //Given
        when(transactionRepo.findAll()).thenReturn(Arrays.asList(
                new Transaction(10.0, System.currentTimeMillis()),
                new Transaction(15.0, System.currentTimeMillis())
        ));

        //When
        transactionService.cleanupAndSummarize();

        //Then
        assertThat(transactionService.getCurrentSummary())
                .hasFieldOrPropertyWithValue("min", 10.0)
                .hasFieldOrPropertyWithValue("max", 15.0)
                .hasFieldOrPropertyWithValue("sum", 25.0)
                .hasFieldOrPropertyWithValue("avg", 12.5)
                .hasFieldOrPropertyWithValue("count", 2);
    }
}