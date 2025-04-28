package com.pluralsight;

import java.util.Scanner;

public class ReportScreen {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LedgerScreen ledgerScreen = new LedgerScreen();
    private static final HomeScreen homeScreen = new HomeScreen();

    private String showReportMenu() {
        System.out.println("Report Options");
        System.out.println("[1] Month To Date");
        System.out.println("[2] Previous Month");
        System.out.println("[3] Year To Date");
        System.out.println("[4] Previous Year");
        System.out.println("[5] Search by Vendor - prompt the user for the vendor name");
        System.out.println("[6] Custom Search");
        System.out.println("[0] Back - Go back to the report page");
        System.out.println("[H] Home - Go back to the home page");

        System.out.print("Select a report option: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public void reportMenuLogic() {
        while (true) {
            String s = showReportMenu();
            switch (s) {
                case "1" -> System.out.println("Month To Date");
                case "2" -> System.out.println("Previous Month");
                case "3" -> System.out.println("Year To Date");
                case "4" -> System.out.println("Previous Year");
                case "5" -> System.out.println("Search by Vendor - prompt the user for the vendor");
                case "6" -> System.out.println("Custom Search");
                case "0" -> {
                    System.out.println("Report - Go back to the report page");
                    ledgerScreen.ledgerMenuLogic();
                }
                case "H" -> {
                    System.out.println("Home - Go back to the home page");
                    homeScreen.mainMenuLogic();
                }
                default -> System.out.println("Select an option");
            }
        }
    }

}
