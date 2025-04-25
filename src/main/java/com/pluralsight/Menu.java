package com.pluralsight;

import java.util.Scanner;

public class Menu {
    public static Scanner scanner = new Scanner(System.in);

    public String showMainMenu() {
        System.out.println("What would you like to do?");
        System.out.println("[D] Add Deposit");
        System.out.println("[P] Make Payment (Debit)");
        System.out.println("[L] Ledger");
        System.out.println("[X] Exit");

        System.out.print("Select an option: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public String showLedgerScreen() {
        System.out.println("Ledger Screen");
        System.out.println("[A] All - Display all entries");
        System.out.println("[D] Deposits - Display only deposit entries");
        System.out.println("[P] Payments - Display only payment entries");
        System.out.println("[R] Reports - View reports and search");
        System.out.println("[H] Home - Return to home screen");

        System.out.print("Select an option: ");
        return scanner.nextLine().trim().toUpperCase();
    }

}
