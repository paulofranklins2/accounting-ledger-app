package com.pluralsight.app;

import com.pluralsight.screens.ScreenUtils;
import com.pluralsight.screens.HomeScreen;
import com.pluralsight.screens.LedgerScreen;
import com.pluralsight.screens.ReportScreen;
import com.pluralsight.services.TransactionService;

import java.util.Scanner;

public class AppContext {
    public static final Scanner scanner = new Scanner(System.in);
    public static final ScreenUtils screenUtils = new ScreenUtils();
    public static final TransactionService transactionService = new TransactionService();
    public static final HomeScreen homeScreen = new HomeScreen();
    public static final LedgerScreen ledgerScreen = new LedgerScreen();
    public static final ReportScreen reportScreen = new ReportScreen();
}
