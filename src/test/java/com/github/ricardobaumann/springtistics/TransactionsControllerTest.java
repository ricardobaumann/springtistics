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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}