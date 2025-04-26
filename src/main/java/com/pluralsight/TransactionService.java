package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import static com.pluralsight.Main.scanner;

public class TransactionService {
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    private void logTransaction(Transaction getTransaction) {
        var localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        var stringBuilder = new StringBuilder();
        stringBuilder
                .append(localDate).append("|")
                .append(localTime).append("|")
                .append(getTransaction.description()).append("|")
                .append(getTransaction.vendor()).append("|")
                .append(getTransaction.amount()).append("\n");

        try (var fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(stringBuilder.toString().toUpperCase().trim());
        } catch (Exception e) {
            System.out.println("Error while trying to log transaction to file");
        }
    }

    public void retrieveTransaction(String option) {
        try (var bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            ArrayList<String> transactions = new ArrayList<>();
            System.out.println("Option: " + option);

            while ((line = bufferedReader.readLine()) != null) {
                var lineIsPayment = Pattern.compile("-").matcher(line).find();

                if (option.equals("A")) {
                    System.out.println(line);
                }
                if (option.equals("D") && !lineIsPayment) {
                    System.out.println(line);
                }
                if (option.equals("P") && lineIsPayment) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while trying to read transactions from file");
        }
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
                var newTransaction = new Transaction(description, vendor, amount);
                logTransaction(newTransaction);
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input" + e.getMessage());
            }
        }
    }
}
