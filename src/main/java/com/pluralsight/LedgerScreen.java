package com.pluralsight;

import static com.pluralsight.Main.*;

public class LedgerScreen {
    private String showLedgerScreen() {
        System.out.println("Ledger Screen");
        System.out.println("[A] All - Display all entries");
        System.out.println("[D] Deposits - Display only deposit entries");
        System.out.println("[P] Payments - Display only payment entries");
        System.out.println("[R] Reports - View reports and search");
        System.out.println("[H] Home - Return to home screen");

        try {
            System.out.print("Select an option: ");
            return scanner.nextLine().trim().toUpperCase();
        } finally {
            clearScreen.clearScreen();
        }
    }

    public void ledgerMenuLogic() {
        while (true) {
            String s = showLedgerScreen();
            switch (s) {
                case "A" -> transactionService.readAllTransactions();
                case "D" -> System.out.println("Deposits");
                case "P" -> System.out.println("Payments");
                case "R" -> reportScreen.reportMenuLogic();
                case "H" -> {
                    System.out.println("Home - Return to home screen");
                    homeScreen.mainMenuLogic();
                }
                default -> System.out.println("Select an option");
            }
        }
    }
}
