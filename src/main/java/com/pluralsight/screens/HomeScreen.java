package com.pluralsight.screens;

import com.pluralsight.gui.AccountingLedgerGUI;

import javax.swing.*;

import static com.pluralsight.app.AppContext.*;
import static com.pluralsight.services.InputHelper.stringInput;

public class HomeScreen {

    private String showMainMenu() {
        clearScreen.cleanPreviousScreen();
        System.out.println("******************* Main Screen *******************");
        System.out.println("[D] Add Deposit");
        System.out.println("[P] Make Payment");
        System.out.println("[L] Ledger");
        System.out.println("[X] Exit");

        try {
            return stringInput("Select an option: ").trim().toUpperCase();
        } finally {
            clearScreen.cleanPreviousScreen();
        }
    }

    public void mainMenuLogic() {
        while (true) {
            var option = new HomeScreen().showMainMenu();
            switch (option) {
                case "D", "P" -> transactionService.requestTransactionInformation(option);
                case "L" -> ledgerScreen.ledgerMenuLogic();
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
                clearScreen.cleanPreviousScreen();
                System.out.println("[1] CLI");
                System.out.println("[2] UI **Working In Progress**");
                System.out.println("[0] Exit");
                var option = stringInput("Choose option: ").trim();
                switch (option) {
                    case "1" -> mainMenuLogic();
                    case "2" -> SwingUtilities.invokeLater(AccountingLedgerGUI::new);
                    case "0" -> System.exit(0);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
