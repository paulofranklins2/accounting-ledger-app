package com.pluralsight;

import java.time.LocalDate;

import static com.pluralsight.AppContext.*;
import static com.pluralsight.InputHelper.stringInput;

public class ReportScreen {

    private String showReportMenu() {
        System.out.println("******************* Report Screen *******************");
        System.out.println("[1] Month To Date");
        System.out.println("[2] Previous Month");
        System.out.println("[3] Year To Date");
        System.out.println("[4] Previous Year");
        System.out.println("[5] Search by Vendor");
        System.out.println("[6] Custom Search");
        System.out.println("[0] Back - Go back to the report page");
        System.out.println("[H] Home - Go back to the home page");

        try {
            return stringInput("Select a report option: ").trim().toUpperCase();
        } finally {
            clearScreen.cleanPreviousScreen();
        }
    }

    public void reportMenuLogic() {
        while (true) {
            String option = showReportMenu();
            switch (option) {
                case "1" -> printMonthToDate();
                case "2" -> printPreviousMonth();
                case "3" -> printYearToDate();
                case "4" -> printPreviousYear();
                case "5" -> searchByVendor(stringInput("Enter vendor name: "));
                case "6" -> {
                    LocalDate start = LocalDate.parse(stringInput("Enter start date (yyyy-mm-dd): "));
                    LocalDate end = LocalDate.parse(stringInput("Enter end date (yyyy-mm-dd): "));
                    customSearch(start, end);
                }
                case "0" -> {
                    ledgerScreen.ledgerMenuLogic();
                    return;
                }
                case "H" -> {
                    homeScreen.mainMenuLogic();
                    return;
                }
                default -> {
                    System.out.println("Select a valid option");
                }
            }
        }
    }

    public void printMonthToDate() {
        var now = LocalDate.now();
        var firstDayOfMonth = now.withDayOfMonth(1);

        printTransactionsBetween(firstDayOfMonth, now);
    }

    public void printPreviousMonth() {
        var today = LocalDate.now();
        var first = today.minusMonths(1).withDayOfMonth(1);
        var last = today.withDayOfMonth(1).minusDays(1);

        printTransactionsBetween(first, last);
    }

    public void printYearToDate() {
        var first = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        var today = LocalDate.now();

        printTransactionsBetween(first, today);
    }

    public void printPreviousYear() {
        var lastYear = LocalDate.now().getYear() - 1;
        var first = LocalDate.of(lastYear, 1, 1);
        var last = LocalDate.of(lastYear, 12, 31);

        printTransactionsBetween(first, last);
    }

    public void searchByVendor(String vendor) {
        for (var transaction : transactionService.getTransactions()) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                printTransaction(transaction);
            }
        }
        clearScreen.cleanLogScreen();
    }

    public void customSearch(LocalDate start, LocalDate end) {
        printTransactionsBetween(start, end);
    }

    private void printTransactionsBetween(LocalDate start, LocalDate end) {
        for (var transaction : transactionService.getTransactions()) {
            LocalDate date = transaction.getTransactionDate();
            if (!date.isBefore(start) && !date.isAfter(end)) {
                printTransaction(transaction);
            }
        }
        clearScreen.cleanLogScreen();
    }

    private void printTransaction(Transaction transaction) {
        System.out.println(transaction.getTransactionDate() + "|" + transaction.getTransactionTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getTransactionType() + "|" + transaction.getAmount());
    }

}
