package com.pluralsight.screens;

import com.pluralsight.gui.AccountingLedgerGUI;

import javax.swing.*;

import static com.pluralsight.app.AppContext.*;
import static com.pluralsight.services.InputHelper.stringInput;

public class HomeScreen {

    private String promptMainMenuOption() {
        screenUtils.clearConsole();
        System.out.println("******************* Main Screen *******************");
        System.out.println("[D] Add Deposit");
        System.out.println("[P] Make Payment");
        System.out.println("[L] Ledger");
        System.out.println("[X] Exit");

        try {
            return stringInput("Select an option: ").trim().toUpperCase();
        } finally {
            screenUtils.clearConsole();
        }
    }

    public void handleMainMenu() {
        while (true) {
            var option = promptMainMenuOption();
            switch (option) {
                case "D", "P" -> transactionService.createTransactionFromInput(option);
                case "L" -> ledgerScreen.handleLedgerMenu();
                case "X" -> {
                    System.out.println("Thank you for using accounting ledger app");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void start() {
        while (true) {
            try {
                screenUtils.clearConsole();
                System.out.println("[1] CLI");
                System.out.println("[2] UI **Working In Progress**");
                System.out.println("[0] Exit");
                var option = stringInput("Choose option: ").trim();
                switch (option) {
                    case "1" -> handleMainMenu();
                    case "2" -> SwingUtilities.invokeLater(AccountingLedgerGUI::new);
                    case "0" -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Invalid option" + e.getMessage());
            }
        }
    }
}
