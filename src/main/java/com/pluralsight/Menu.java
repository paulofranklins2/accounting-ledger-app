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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select an option: ");
        return scanner.nextLine().trim().toUpperCase();
    }



}
