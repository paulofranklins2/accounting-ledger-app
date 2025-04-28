package com.pluralsight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private String description;
    private String vendor;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private LocalDate transactionDate;
    private LocalTime transactionTime;

    public Transaction(String description, String vendor, String transactionType, BigDecimal amount, LocalTime transactionTime, LocalDate transactionDate) {
        this.description = description;
        this.vendor = vendor;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionTime = transactionTime;
        this.transactionDate = transactionDate;
    }

    public Transaction(String description, String vendor, String transactionType, BigDecimal amount) {
        this.description = description;
        this.vendor = vendor;
        this.transactionType = transactionType;
        this.amount = amount;
    }
}