package com.pluralsight;

import java.util.Scanner;

public class LedgerScreen {
    private static final HomeScreen homeScreen = new HomeScreen();
    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionService transactionService = new TransactionService();
    private static final ReportScreen reportScreen = new ReportScreen();

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
//            clearScreen.clearScreen();
        }
    }

    public void ledgerMenuLogic() {
        while (true) {
            var option = showLedgerScreen().toUpperCase();
            switch (option) {
                case "A", "D", "P" -> transactionService.printTransactions(option);
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
