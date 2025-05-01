package com.pluralsight.services;


import java.math.BigDecimal;

import static com.pluralsight.app.AppContext.scanner;

public class InputHelper {

    public static String stringInput(String input) {
        while (true) {
            try {
                System.out.print(input);
                return scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static BigDecimal bigDecimalInput(String input, String option) {
        while (true) {
            System.out.print(input);

            try {
                if (option.equalsIgnoreCase("P"))
                    return BigDecimal.valueOf(scanner.nextDouble()).negate();
                return BigDecimal.valueOf(Math.abs(scanner.nextDouble()));
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
