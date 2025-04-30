package com.pluralsight.screens;

import static com.pluralsight.services.InputHelper.stringInput;

public class ClearScreen {

    public void cleanPreviousScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void cleanLogScreen() {
        stringInput("Press ENTER to continue: ");
        cleanPreviousScreen();
    }
}
