package com.pluralsight.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private String description;
    private String vendor;
    private String transactionType;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private LocalTime transactionTime;

    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String description, String vendor, String transactionType, BigDecimal amount) {
        this.transactionTime = transactionTime;
        this.transactionDate = transactionDate;
        this.description = description;
        this.vendor = vendor;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.getTransactionDate() + "|" +
                this.getTransactionTime() + "|" +
                this.getDescription() + "|" +
                this.getVendor() + "|" +
                this.getTransactionType() + "|" +
                this.getAmount();
    }

    public String saveTransactionToString() {
        return this.getTransactionDate() + "|" +
                this.getTransactionTime() + "|" +
                this.getDescription() + "|" +
                this.getVendor() + "|" +
                this.getTransactionType() + "|" +
                this.getAmount() + "\n";
    }
}