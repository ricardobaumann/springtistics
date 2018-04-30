package com.github.ricardobaumann.springtistics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionsController transactionsController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).build();
    }

    @Test
    public void shouldReturnOkOnSuccessfulCreation() throws Exception {
        //Given
        when(transactionService.createTransaction(any())).thenReturn(true);

        //When //Then
        mockMvc.perform(post("/transactions")
                .content("{\"amount\": 12.0, \"timestamp\" : 100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNoContentOnFailedCreation() throws Exception {
        //Given
        when(transactionService.createTransaction(any())).thenReturn(false);

        //When //Then
        mockMvc.perform(post("/transactions")
                .content("{\"amount\": 12.0, \"timestamp\" : 100}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestOnInvalidInput() throws Exception {
        //When //Then
        mockMvc.perform(post("/transactions")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(transactionService, never()).createTransaction(any());
    }

    @Test
    public void shouldMapTransactionSummary() throws Exception {
        //Given
        TransactionsSummary summary = new TransactionsSummary(100.0, 10.0, 20.0, 5.0, 10);
        when(transactionService.getCurrentSummary()).thenReturn(summary);

        //When //Then
        mockMvc.perform(get("/transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(summary.getSum())))
                .andExpect(jsonPath("$.avg", is(summary.getAvg())))
                .andExpect(jsonPath("$.max", is(summary.getMax())))
                .andExpect(jsonPath("$.min", is(summary.getMin())))
                .andExpect(jsonPath("$.count", is(summary.getCount())))

        ;
    }
}