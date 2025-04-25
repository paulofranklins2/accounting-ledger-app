package com.pluralsight;

public class SearchInventoryApp {
    private final Menu menu = new Menu();

    public void start() {
        while (true) {
            var option = menu.showMainMenu();
            switch (option) {
                case "D" -> System.out.println("Add Deposit");
                case "P" -> System.out.println("Make Payment");
                case "L" -> System.out.println("Ledger");
                case "X" -> {
                    System.out.println("Thank you for using accounting ledger app");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        }
    }
}
