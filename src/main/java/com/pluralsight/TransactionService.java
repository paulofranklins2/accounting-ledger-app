package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.InputHelper.bigDecimalInput;
import static com.pluralsight.InputHelper.stringInput;

public class TransactionService {
    private static final String FILE_PATH = "src/main/resources/transactions.csv";
    private static final Scanner scanner = new Scanner(System.in);


    private void logTransaction(Transaction transaction) {
        var stringBuilder = new StringBuilder();
        stringBuilder
                .append(transaction.getTransactionDate()).append("|")
                .append(transaction.getTransactionTime()).append("|")
                .append(transaction.getDescription()).append("|")
                .append(transaction.getVendor()).append("|")
                .append(transaction.getTransactionType()).append("|")
                .append(transaction.getAmount()).append("\n");

        try (var fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(stringBuilder.toString().toUpperCase());
        } catch (Exception e) {
            System.out.println("Error while trying to log transaction to file");
        }
    }

    private List<Transaction> getTransactions() {
        var transactions = new ArrayList<Transaction>();
        try (var bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                var split = line.split("\\|");
                transactions.add(new Transaction(split[2], split[3], split[4], new BigDecimal(split[5]), LocalTime.parse(split[1]), LocalDate.parse(split[0])));
            }
        } catch (Exception e) {
            System.out.println("Error while trying to read transactions from file");
        }
        return transactions;
    }

    public void printTransactions(String option) {
        getTransactions().forEach(transaction -> {
            var textFormat = (transaction.getTransactionDate() + "|" + transaction.getTransactionTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getTransactionType() + "|" + transaction.getAmount() + "\n").toUpperCase().trim();

            if (option.equals("P") && transaction.getTransactionType().equals("P")) {
                System.out.println(textFormat);
            }
            if (option.equals("D") && transaction.getTransactionType().equals("D")) {
                System.out.println(textFormat);
            }
            if (option.equals("A")) {
                System.out.println(textFormat);
            }
        });
    }

    public void requestTransactionInformation(String option) {
        while (true) {
            try {
                System.out.println("Requesting transaction information");
                var description = stringInput("Enter description: ");
                var vendor = stringInput("Enter vendor: ");
                var amount = bigDecimalInput("Enter amount: ", option);
                scanner.nextLine();
                var date = generateDate();
                var time = generateTime();

                logTransaction(new Transaction(description, vendor, option, amount, time, date));
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input" + e.getMessage());
            }
        }
    }

    private LocalTime generateTime() {
        while (true) {
            try {
                if (stringInput("Auto Time? (Y/N): ").equalsIgnoreCase("Y"))
                    return LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                String input = stringInput("Enter time (HH:MM): ");
                return LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm")).plusSeconds(LocalTime.now().getSecond());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:MM format.");
            }
        }
    }

    private LocalDate generateDate() {
        while (true) {
            try {
                if (stringInput("Auto Date? (Y/N): ").equalsIgnoreCase("Y"))
                    return LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                String input = stringInput("Enter date (YYYY-MM-DD): ");
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
}