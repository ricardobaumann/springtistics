package com.github.ricardobaumann.springtistics;

public class TransactionsSummary {
    private final Double sum;

    private final Double avg;

    private final Double max;

    private final Double min;

    private final Integer count;

    TransactionsSummary(Double sum, Double avg, Double max, Double min, Integer count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public Double getAvg() {
        return avg;
    }

    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min;
    }

    public Integer getCount() {
        return count;
    }
}
