package com.github.ricardobaumann.springtistics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionsController {

    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> post(@RequestBody @Valid Transaction transaction) {
        boolean success = transactionService.createTransaction(transaction);
        return success ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/transactions")
    public TransactionsSummary get() {
        return transactionService.getCurrentSummary();
    }

}
