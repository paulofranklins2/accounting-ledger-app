package com.pluralsight;

import java.util.Scanner;

public class HomeScreen {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionService transactionService = new TransactionService();
    private static final LedgerScreen ledgerScreen = new LedgerScreen();

    private String showMainMenu() {
        System.out.println("What would you like to do?");
        System.out.println("[D] Add Deposit");
        System.out.println("[P] Make Payment");
        System.out.println("[L] Ledger");
        System.out.println("[X] Exit");

        try {
            System.out.print("Select an option: ");
            return scanner.nextLine().trim().toUpperCase();
        } finally {
//            clearScreen.clearScreen();
        }
    }

    public void mainMenuLogic() {
        while (true) {
            var option = new HomeScreen().showMainMenu();
            switch (option) {
                case "D", "P" -> transactionService.requestTransactionInformation(option);
                case "L" -> ledgerScreen.ledgerMenuLogic();
                case "X" -> {
                    System.out.println("Thank you for using accounting ledger app");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void start() {
        mainMenuLogic();
    }
}
