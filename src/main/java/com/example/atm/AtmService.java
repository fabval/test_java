

package com.example.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AtmService {

    private Scanner scanner;
    private Map<String, Account> accounts;

    public AtmService() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        this.accounts.put("1234", new Account("1234", "1234", 100.0));
    }

    public void run() {
        System.out.println("Welcome to the ATM system. Please enter your card number and PIN:");
        String cardNumber = scanner.nextLine();
        String pin = scanner.nextLine();
        if (authenticate(cardNumber, pin)) {
            showMenu(cardNumber);
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
    }

    private boolean authenticate(String cardNumber, String pin) {
        if (accounts.containsKey(cardNumber)) {
            return accounts.get(cardNumber).getPin().equals(pin);
        }
        return false;
    }

    private void showMenu(String cardNumber) {
        System.out.println("Main menu:");
        System.out.println("1. Check balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdrawal");
        System.out.println("4. Exit");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline left-over
        switch (choice) {
            case 1:
                checkBalance(cardNumber);
                break;
            case 2:
                deposit(cardNumber);
                break;
            case 3:
                withdrawal(cardNumber);
                break;
            case 4:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMenu(cardNumber);
        }
    }

    private void checkBalance(String cardNumber) {
        System.out.println("Your balance is: $" + accounts.get(cardNumber).getBalance());
        showMenu(cardNumber);
    }

    private void deposit(String cardNumber) {
        System.out.println("Enter deposit amount:");
        String amount = scanner.nextLine();
        if (isNumeric(amount)) {
            double depositAmount = Double.parseDouble(amount);
            accounts.get(cardNumber).deposit(depositAmount);
            System.out.println("Deposit successful. Your new balance is: $" + accounts.get(cardNumber).getBalance());
            showMenu(cardNumber);
        } else {
            System.out.println("Invalid amount. Please try again.");
            deposit(cardNumber);
        }
    }

    private void withdrawal(String cardNumber) {
        System.out.println("Enter withdrawal amount:");
        String amount = scanner.nextLine();
        if (isNumeric(amount)) {
            double withdrawalAmount = Double.parseDouble(amount);
            if (accounts.get(cardNumber).getBalance() >= withdrawalAmount) {
                accounts.get(cardNumber).withdrawal(withdrawalAmount);
                System.out.println("Withdrawal successful. Your new balance is: $" + accounts.get(cardNumber).getBalance());
                showMenu(cardNumber);
            } else {
                System.out.println("Insufficient balance. Please try again.");
                withdrawal(cardNumber);
            }
        } else {
            System.out.println("Invalid amount. Please try again.");
            withdrawal(cardNumber);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private class Account {
        private String cardNumber;
        private String pin;
        private double balance;

        public Account(String cardNumber, String pin, double balance) {
            this.cardNumber = cardNumber;
            this.pin = pin;
            this.balance = balance;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public String getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            this.balance += amount;
        }

        public void withdrawal(double amount) {
            this.balance -= amount;
        }
    }
}