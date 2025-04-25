package com.pluralsight;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static final HomeScreen homeScreen = new HomeScreen();
    public static final ClearScreen clearScreen = new ClearScreen();
    public static final TransactionService transactionService = new TransactionService();
    public static final LedgerScreen ledgerScreen = new LedgerScreen();
    public static final ReportScreen reportScreen = new ReportScreen();

    public static void main(String[] args) {
        new HomeScreen().start();
    }
}