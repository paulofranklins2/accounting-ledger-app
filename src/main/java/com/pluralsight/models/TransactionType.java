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

    public static String fromValue(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                String name = type.name().toLowerCase();
                return name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }
        return null;
    }
}
