package com.pluralsight.models;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("D"),
    PAYMENT("P"),
    WITHDRAWAL("W"),
    ALL("A");

    private final String value;

    TransactionType(String description) {
        this.value = description;
    }
}
