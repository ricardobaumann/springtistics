package com.github.ricardobaumann.springtistics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TransactionService {

    private static final long VALID_RANGE = 60;
    private final TransactionRepo transactionRepo;
    private TransactionsSummary currentTransactionsSummary;

    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    boolean createTransaction(Transaction transaction) {
        Instant sixtySecsAgo = Instant.now().minusSeconds(VALID_RANGE);
        if (Instant.ofEpochMilli(transaction.getTimestamp()).isBefore(sixtySecsAgo)) {
            return Boolean.FALSE;
        }
        transactionRepo.save(transaction);
        return Boolean.TRUE;
    }

    @Scheduled(fixedRate = 1000)
    void cleanupAndSummarize() {
        transactionRepo.deleteByTimestampLesserThan(VALID_RANGE);
        currentTransactionsSummary = loadSummary();
    }

    private TransactionsSummary loadSummary() {
        AtomicReference<Double> max = new AtomicReference<>(0.0);
        AtomicReference<Double> min = new AtomicReference<>(0.0);
        AtomicInteger count = new AtomicInteger(0);
        double sum = transactionRepo.findAll()
                .stream()
                .peek(transaction -> {
                    if (min.get() == 0 || transaction.getAmount() < min.get()) {
                        min.set(transaction.getAmount());
                    }
                    if (max.get() == 0 || transaction.getAmount() > max.get()) {
                        max.set(transaction.getAmount());
                    }
                    count.incrementAndGet();
                }).mapToDouble(Transaction::getAmount)
                .sum();
        double avg = count.get() > 0 ? sum / count.get() : 0;
        return new TransactionsSummary(sum, avg, max.get(), min.get(), count.get());
    }

    TransactionsSummary getCurrentSummary() {
        return currentTransactionsSummary;
    }
}
