package com.pluralsight;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("D"),
    PAYMENT("P"),
    WITHDRAWAL("W"),
    ALL("A");

    private final String shortName;

    TransactionType(String description) {
        this.shortName = description;
    }
}
