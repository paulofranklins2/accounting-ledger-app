package com.pluralsight;

import static com.pluralsight.AppContext.*;

public class LedgerScreen {

    private String showLedgerScreen() {
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
//            clearScreen.clearScreen();
        }
    }

    public void ledgerMenuLogic() {
        while (true) {
            var option = showLedgerScreen();
            switch (option) {
                case "A", "D", "P" -> {
                    clearScreen.cleanPreviousScreen();
                    transactionService.printTransactions(option);
                }
                case "R" -> {
                    clearScreen.cleanPreviousScreen();
                    reportScreen.reportMenuLogic();
                }
                case "H" -> {
                    clearScreen.cleanPreviousScreen();
                    homeScreen.mainMenuLogic();
                    return;
                }
                default -> System.out.println("Select an option");
            }
        }
    }
}
