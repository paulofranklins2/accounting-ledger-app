package com.pluralsight;

import static com.pluralsight.Main.scanner;

public class LedgerScreen {
    private static final MainMenu menu = new MainMenu();
    private static final ClearScreen clearScreen = new ClearScreen();

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
                case "A" -> System.out.println("All");
                case "D" -> System.out.println("Deposits");
                case "P" -> System.out.println("Payments");
                case "R" -> System.out.println("Reports");
                case "H" -> {
                    System.out.println("Home - Return to home screen");
                    menu.mainMenuLogic();
                }
                default -> System.out.println("Select an option");
            }
        }
    }
}
