package com.pluralsight;

import static com.pluralsight.Main.scanner;

public class HomeScreen {
    private final LedgerScreen ledgerScreen = new LedgerScreen();
    private final ClearScreen clearScreen = new ClearScreen();
    private final TransactionService transactionService = new TransactionService();

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
            clearScreen.clearScreen();
        }
    }

    public void mainMenuLogic() {
        while (true) {
            var option = new HomeScreen().showMainMenu();
            switch (option) {
                case "D" -> transactionService.logTransaction();
                case "P" -> System.out.println("Make Payment");
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
