package com.pluralsight;


import java.math.BigDecimal;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String stringInput(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

    public static BigDecimal bigDecimalInput(String input, String option) {
        System.out.print(input);
        if (option.equalsIgnoreCase("P")) {
            return BigDecimal.valueOf(scanner.nextDouble()).negate();
        }
        return BigDecimal.valueOf(scanner.nextDouble());
    }

    public static boolean boolInput(String input) {
        return input.equalsIgnoreCase("Y");
    }

}
