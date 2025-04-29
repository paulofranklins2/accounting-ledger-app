package com.pluralsight;

import static com.pluralsight.InputHelper.stringInput;

public class ClearScreen {

    public void cleanPreviousScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void cleanLogScreen() {
        stringInput("Press any key to continue: ");
        cleanPreviousScreen();
    }
}
