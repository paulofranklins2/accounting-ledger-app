package com.pluralsight;


import java.math.BigDecimal;

import static com.pluralsight.AppContext.scanner;

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
        System.out.print(input);
        if (option.equalsIgnoreCase("P")) {
            return BigDecimal.valueOf(scanner.nextDouble()).negate();
        }
        return BigDecimal.valueOf(scanner.nextDouble());
    }
}
