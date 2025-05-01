package com.pluralsight.gui;

import com.pluralsight.models.Transaction;
import com.pluralsight.services.TransactionService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AccountingLedgerGUI extends JFrame {
    private final TransactionService transactionService = new TransactionService();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private JTextArea transactionArea;

    public AccountingLedgerGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Accounting Ledger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(createLedgerPanel(), "LEDGER");
        mainPanel.add(createReportPanel(), "REPORTS");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Accounting Ledger", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonPanel.setBorder(new EmptyBorder(50, 150, 50, 150));

        var depositButton = createStyledButton("Add Deposit");
        var paymentButton = createStyledButton("Make Payment");
        var ledgerButton = createStyledButton("View Ledger");
        var exitButton = createStyledButton("Exit");

        depositButton.addActionListener(e -> showTransactionDialog("D"));
        paymentButton.addActionListener(e -> showTransactionDialog("P"));
        ledgerButton.addActionListener(e -> cardLayout.show(mainPanel, "LEDGER"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(depositButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(ledgerButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLedgerPanel() {
        var panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Main content panel
        var contentPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        contentPanel.setBorder(new EmptyBorder(50, 150, 50, 150));

        // Create styled buttons
        var allEntriesButton = createStyledButton("All Entries");
        var depositsButton = createStyledButton("Deposits Only");
        var paymentsButton = createStyledButton("Payments Only");
        var reportsButton = createStyledButton("View Reports");
        var backButton = createStyledButton("Back to Home");

        // Add action listeners
        allEntriesButton.addActionListener(e -> showTransactionsDialog(
                transactionService.getTransactions(),
                "All Transactions"
        ));

        depositsButton.addActionListener(e -> showTransactionsDialog(
                transactionService.getTransactions().stream()
                        .filter(t -> t.getTransactionType().equals("D"))
                        .toList(),
                "Deposits Only"
        ));

        paymentsButton.addActionListener(e -> showTransactionsDialog(
                transactionService.getTransactions().stream()
                        .filter(t -> t.getTransactionType().equals("P"))
                        .toList(),
                "Payments Only"
        ));

        reportsButton.addActionListener(e -> cardLayout.show(mainPanel, "REPORTS"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        // Add components
        contentPanel.add(allEntriesButton);
        contentPanel.add(depositsButton);
        contentPanel.add(paymentsButton);
        contentPanel.add(reportsButton);
        contentPanel.add(backButton);

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createReportPanel() {
        var panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Navigation Bar
        var navPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        var backToLedgerButton = createStyledButton("Back to Ledger");
        var backToHomeButton = createStyledButton("Back to Home");
        backToLedgerButton.addActionListener(e -> cardLayout.show(mainPanel, "LEDGER"));
        backToHomeButton.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        navPanel.add(backToLedgerButton);
        navPanel.add(backToHomeButton);
        panel.add(navPanel, BorderLayout.NORTH);

        // Report Options
        var reportPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        reportPanel.setBorder(new EmptyBorder(20, 100, 20, 100));
        String[] reports = {
                "Month to Date",
                "Previous Month",
                "Year to Date",
                "Previous Year",
                "Search by Vendor",
                "Custom Search"
        };

        for (String report : reports) {
            JButton button = createStyledButton(report);
            button.addActionListener(new ReportButtonListener());
            reportPanel.add(button);
        }

        var scrollPane = new JScrollPane(reportPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text) {
        var button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return button;
    }

    private void showTransactionDialog(String type) {
        var dialog = new JDialog(this, type.equals("D") ? "Add Deposit" : "Make Payment", true);
        var panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        var descriptionField = new JTextField();
        var vendorField = new JTextField();
        var amountField = new JTextField();
        var dateField = new JTextField(LocalDate.now().toString());
        var timeField = new JTextField(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Vendor:"));
        panel.add(vendorField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Time (HH:MM):"));
        panel.add(timeField);

        var submitButton = createStyledButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                var transaction = new Transaction(
                        LocalDate.parse(dateField.getText()), LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm")), descriptionField.getText(),
                        vendorField.getText(),
                        type,
                        new BigDecimal(amountField.getText())
                );
                transactionService.saveTransactionToFile(transaction);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input format");
            }
        });

        panel.add(new JLabel());
        panel.add(submitButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showTransactionsDialog(List<Transaction> transactions, String title) {
        JDialog dialog = new JDialog(this, title, true);
        var textArea = new JTextArea(20, 60);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        var stringBuilder = new StringBuilder();
        var timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Header
        stringBuilder.append(String.format("%-12s %-8s %-20s %-15s %-8s %10s\n",
                "Date", "Time", "Description", "Vendor", "Type", "Amount"));
        stringBuilder.append("-".repeat(80)).append("\n");

        // Transactions
        for (Transaction transaction : transactions) {
            stringBuilder.append(String.format("%-12s %-8s %-20s %-15s %-8s %10.2f\n",
                    transaction.getTransactionDate(),
                    transaction.getTransactionTime().format(timeFormatter),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getTransactionType(),
                    transaction.getAmount()));
        }

        textArea.setText(stringBuilder.toString());
        var scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        var buttonPanel = new JPanel();
        var closeButton = createStyledButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private class ReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var command = e.getActionCommand();
            try {
                switch (command) {
                    case "Month to Date":
                        handleDateFilter(LocalDate.now().withDayOfMonth(1), LocalDate.now(), "Month to Date");
                        break;
                    case "Previous Month":
                        LocalDate lastMonth = LocalDate.now().minusMonths(1);
                        handleDateFilter(lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()), "Previous Month");
                        break;
                    case "Year to Date":
                        handleDateFilter(LocalDate.now().withDayOfYear(1), LocalDate.now(), "Year to Date");
                        break;
                    case "Previous Year":
                        int lastYear = LocalDate.now().getYear() - 1;
                        handleDateFilter(LocalDate.of(lastYear, 1, 1), LocalDate.of(lastYear, 12, 31), "Previous Year");
                        break;
                    case "Search by Vendor":
                        handleVendorSearch();
                        break;
                    case "Custom Search":
                        handleCustomSearch();
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AccountingLedgerGUI.this, "Error: " + ex.getMessage());
            }
        }

        private void handleDateFilter(LocalDate start, LocalDate end, String title) {
            var filtered = transactionService.getTransactions().stream()
                    .filter(t -> !t.getTransactionDate().isBefore(start) && !t.getTransactionDate().isAfter(end))
                    .toList();

            showFilteredTransactions(filtered, title + " (" + start + " to " + end + ")");
        }

        private void handleVendorSearch() {
            var vendor = JOptionPane.showInputDialog(AccountingLedgerGUI.this, "Enter vendor name:");
            if (vendor == null || vendor.trim().isEmpty()) return;

            var filtered = transactionService.getTransactions().stream()
                    .filter(t -> t.getVendor().equalsIgnoreCase(vendor.trim()))
                    .toList();

            showFilteredTransactions(filtered, "Vendor Search: " + vendor);
        }

        private void handleCustomSearch() {
            // Create input panel with consistent styling
            var panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBorder(new EmptyBorder(15, 20, 15, 20));

            // Create input fields with tooltips
            var startField = new JTextField();
            startField.setToolTipText("Leave blank for no start date filter");
            var endField = new JTextField();
            endField.setToolTipText("Leave blank for no end date filter");
            var descField = new JTextField();
            descField.setToolTipText("Partial match, case insensitive");
            var vendorField = new JTextField();
            vendorField.setToolTipText("Exact match, case insensitive");
            var amountField = new JTextField();
            amountField.setToolTipText("Exact amount match");

            // Add components with styled labels
            panel.add(createInputLabel("Start Date (YYYY-MM-DD):"));
            panel.add(startField);
            panel.add(createInputLabel("End Date (YYYY-MM-DD):"));
            panel.add(endField);
            panel.add(createInputLabel("Description Contains:"));
            panel.add(descField);
            panel.add(createInputLabel("Vendor:"));
            panel.add(vendorField);
            panel.add(createInputLabel("Amount:"));
            panel.add(amountField);

            // Show input dialog
            int result = JOptionPane.showConfirmDialog(
                    AccountingLedgerGUI.this,
                    panel,
                    "Custom Search Filters",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Parse inputs with null-safe methods
                    var startDate = parseDate(startField.getText().trim());
                    var endDate = parseDate(endField.getText().trim());
                    var description = descField.getText().trim().toLowerCase();
                    var vendor = vendorField.getText().trim();
                    var amount = parseAmount(amountField.getText().trim());

                    // Validate date range
                    if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
                        throw new IllegalArgumentException("Start date cannot be after end date");
                    }

                    // Apply filters
                    List<Transaction> filtered = transactionService.getTransactions().stream()
                            .filter(t -> startDate == null || !t.getTransactionDate().isBefore(startDate))
                            .filter(t -> endDate == null || !t.getTransactionDate().isAfter(endDate))
                            .filter(t -> description.isEmpty() ||
                                    t.getDescription().toLowerCase().contains(description))
                            .filter(t -> vendor.isEmpty() ||
                                    t.getVendor().equalsIgnoreCase(vendor))
                            .filter(t -> amount == null ||
                                    t.getAmount().compareTo(amount) == 0)
                            .toList();

                    // Show results
                    showFilteredTransactions(filtered, "Custom Search Results");

                } catch (DateTimeParseException e) {
                    showErrorDialog("Invalid date format. Please use YYYY-MM-DD.");
                } catch (NumberFormatException e) {
                    showErrorDialog("Invalid amount format. Please enter a numeric value.");
                } catch (IllegalArgumentException e) {
                    showErrorDialog(e.getMessage());
                }
            }
        }

        private JLabel createInputLabel(String text) {
            var label = new JLabel(text);
            label.setFont(new Font("Tahoma", Font.PLAIN, 12));
            return label;
        }

        private LocalDate parseDate(String input) throws DateTimeParseException {
            return input.isEmpty() ? null : LocalDate.parse(input);
        }

        private BigDecimal parseAmount(String input) throws NumberFormatException {
            return input.isEmpty() ? null : new BigDecimal(input);
        }

        private void showErrorDialog(String message) {
            JOptionPane.showMessageDialog(
                    AccountingLedgerGUI.this,
                    message,
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        private void showFilteredTransactions(List<Transaction> transactions, String title) {
            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(AccountingLedgerGUI.this, "No transactions found");
                return;
            }

            var dialog = new JDialog(AccountingLedgerGUI.this, title, true);
            var textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

            var stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%-12s %-8s %-20s %-15s %-8s %10s\n",
                    "Date", "Time", "Description", "Vendor", "Type", "Amount"));
            stringBuilder.append("-".repeat(80)).append("\n");

            for (Transaction transaction : transactions) {
                stringBuilder.append(String.format("%-12s %-8s %-20s %-15s %-8s %10.2f\n",
                        transaction.getTransactionDate(),
                        transaction.getTransactionTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getTransactionType(),
                        transaction.getAmount()));
            }

            textArea.setText(stringBuilder.toString());
            var scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(800, 400));
            dialog.add(scrollPane);
            dialog.pack();
            dialog.setLocationRelativeTo(AccountingLedgerGUI.this);
            dialog.setVisible(true);
        }
    }
}