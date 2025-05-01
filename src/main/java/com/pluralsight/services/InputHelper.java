package com.pluralsight.services;


import java.math.BigDecimal;

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
                var input = scanner.nextLine().trim();
                var value = new BigDecimal(input);

                if (transactionType.equalsIgnoreCase("P"))
                    return value.abs().negate();

                return value.abs();
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }
}
