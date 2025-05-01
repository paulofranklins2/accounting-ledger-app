package com.pluralsight.services;

import com.pluralsight.TransactionType;
import com.pluralsight.models.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.pluralsight.app.AppContext.screenUtils;
import static com.pluralsight.app.AppContext.scanner;
import static com.pluralsight.services.InputHelper.bigDecimalInput;
import static com.pluralsight.services.InputHelper.stringInput;

public class TransactionService {
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    public void saveTransactionToFile(Transaction transaction) {
        try (var fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(transaction.logTransationString().toUpperCase());

            System.out.println("Saved: " + transaction.logTransationString().toUpperCase().trim());
            screenUtils.pauseAndClearScreen();
        } catch (Exception e) {
            System.out.println("Error while trying to log transaction to file");
        }
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] split = line.split("\\|");
                    if (split.length != 6) {
                        System.out.println("Skipping malformed line: " + line);
                        continue;
                    }

                    var date = LocalDate.parse(split[0]);
                    var time = LocalTime.parse(split[1]);
                    var description = split[2];
                    var vendor = split[3];
                    var type = split[4];
                    var amount = new BigDecimal(split[5]);

                    transactions.add(new Transaction(description, vendor, type, amount, time, date));
                } catch (Exception ex) {
                    System.out.println("Error parsing line, skipping: " + line);
                }
            }

            transactions.sort(Comparator
                    .comparing(Transaction::getTransactionDate)
                    .thenComparing(Transaction::getTransactionTime)
                    .reversed()
            );
        } catch (Exception e) {
            System.out.println("Error while trying to read transactions from file: " + e.getMessage());
        }
        return transactions;
    }

    public void displayTransactionsByType(String option) {
        if (option.equals(TransactionType.ALL.getShortName())) System.out.println("******************* All Transactions Screen *******************");
        if (option.equals(TransactionType.DEPOSIT.getShortName())) System.out.println("******************* Deposit Screen *******************");
        if (option.equals(TransactionType.PAYMENT.getShortName())) System.out.println("******************* Payment Screen *******************");

        getTransactions().forEach(transaction -> {
            if (option.equals(TransactionType.PAYMENT.getShortName()) && transaction.getTransactionType().equals(TransactionType.PAYMENT.getShortName())) System.out.println(transaction);
            if (option.equals(TransactionType.DEPOSIT.getShortName()) && transaction.getTransactionType().equals(TransactionType.DEPOSIT.getShortName())) System.out.println(transaction);
            if (option.equals(TransactionType.ALL.getShortName())) System.out.println(transaction);
        });
        screenUtils.pauseAndClearScreen();
    }

    public void createTransactionFromInput(String option) {
        if (option.equals(TransactionType.PAYMENT.getShortName())) System.out.println("******************* Payment Screen *******************");
        else System.out.println("******************* Deposit Screen *******************");

        var description = stringInput("Enter Description: ");
        var vendor = stringInput("Enter Vendor: ");
        var amount = bigDecimalInput("Enter Amount: ", option);
        scanner.nextLine();
        var date = promptForTransactionTime();
        var time = promptForTransactionDate();

        saveTransactionToFile(new Transaction(description, vendor, option, amount, time, date));
        screenUtils.clearConsole();
    }

    private LocalTime promptForTransactionDate() {
        while (true) {
            try {
                if (stringInput("Auto Time? (Y/N): ").equalsIgnoreCase("Y"))
                    return LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                var input = stringInput("Enter time (HH:MM): ");
                return LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm")).plusSeconds(LocalTime.now().getSecond());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:MM format.");
            }
        }
    }

    private LocalDate promptForTransactionTime() {
        while (true) {
            try {
                if (stringInput("Auto Date? (Y/N): ").equalsIgnoreCase("Y"))
                    return LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                var input = stringInput("Enter date (YYYY-MM-DD): ");
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
}