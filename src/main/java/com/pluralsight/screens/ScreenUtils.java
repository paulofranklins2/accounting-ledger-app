package com.pluralsight.screens;

import com.pluralsight.models.TransactionType;

import static com.pluralsight.services.InputHelper.stringInput;

public class ScreenUtils {

    public void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void pauseAndClearScreen() {
        stringInput("\nPress ENTER to continue: ");
        clearConsole();
    }

    public void printTransactionScreenHeader(String type) {
        System.out.println("******************* " + TransactionType.fromValue(type) + " Transactions Screen *******************");
    }
}
