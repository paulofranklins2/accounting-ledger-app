package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class TransactionService {
    private static final String FILE_PATH = "src/main/resources/transactions.csv";

    public void logTransaction() {
        try (var fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write("TransactionID,Amount,Date,Time,Description \n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error while trying to log transaction to file");
        }
    }

    public void readAllTransactions() {
        try (var bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error while trying to read transactions from file");
        }
    }
}
