package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.pluralsight.Main.scanner;

public class TransactionService {
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    private void logTransaction(Transaction transaction) {
        var localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        var stringBuilder = new StringBuilder();
        stringBuilder
                .append(localDate).append("|")
                .append(localTime).append("|")
                .append(transaction.getDescription()).append("|")
                .append(transaction.getVendor()).append("|")
                .append(transaction.getTransactionType()).append("|")
                .append(transaction.getAmount())
                .append("\n");

        try (var fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(stringBuilder.toString().toUpperCase());
        } catch (Exception e) {
            System.out.println("Error while trying to log transaction to file");
        }
    }

    public List<Transaction> getTransactions() {
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
                System.out.print("Enter description: ");
                var description = scanner.nextLine();
                System.out.print("Enter vendor: ");
                var vendor = scanner.nextLine();
                System.out.print("Enter amount: ");
                var amount = scanner.nextBigDecimal();
                if (option.equals("P")) {
                    amount = amount.negate();
                }
                var newTransaction = new Transaction(description, vendor, option, amount);
                logTransaction(newTransaction);
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input" + e.getMessage());
            }
        }
    }
}
