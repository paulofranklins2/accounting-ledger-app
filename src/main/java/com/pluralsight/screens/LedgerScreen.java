package com.pluralsight.screens;

import static com.pluralsight.app.AppContext.*;

public class LedgerScreen {

    private String promptLedgerMenuOption() {
        System.out.println("******************* Ledger Screen *******************");
        System.out.println("[A] All - Display all entries");
        System.out.println("[D] Deposits - Display only deposit entries");
        System.out.println("[P] Payments - Display only payment entries");
        System.out.println("[R] Reports - View reports and search");
        System.out.println("[H] Home - Return to home screen");

        try {
            System.out.print("Select an option: ");
            return scanner.nextLine().trim().toUpperCase();
        } finally {
            screenUtils.clearConsole();
        }
    }

    public void handleLedgerMenu() {
        while (true) {
            var option = promptLedgerMenuOption();
            switch (option) {
                case "A", "D", "P" -> transactionService.displayTransactionsByType(option);
                case "R" -> reportScreen.handleReportMenu();
                case "H" -> {
                    homeScreen.handleMainMenu();
                    return;
                }
                default -> System.out.println("Select an option");
            }
        }
    }
}
