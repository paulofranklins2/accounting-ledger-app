package com.pluralsight.services;


import com.pluralsight.models.TransactionType;

import java.math.BigDecimal;
import java.util.InputMismatchException;

import static com.pluralsight.app.AppContext.scanner;

public class InputHelper {

    public static String stringInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextLine().trim();
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static BigDecimal bigDecimalInput(String prompt, String transactionType) {
        while (true) {
            System.out.print(prompt);
            try {
                var amount = scanner.nextBigDecimal();
                if (transactionType.equalsIgnoreCase(TransactionType.PAYMENT.getValue())) return amount.negate();
                else return amount.abs();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }
}
