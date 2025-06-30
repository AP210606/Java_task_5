package Task_5;

import java.util.*;

public class Account {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected List<Transaction> transactionHistory;

    private static int nextAccountNumber = 1001;

    public Account(String accountHolderName, double initialBalance) {
        this.accountNumber = String.valueOf(nextAccountNumber++);
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        System.out.printf("Account created successfully for %s. Account Number: %s%n",
                accountHolderName, this.accountNumber);
        if (initialBalance > 0) {
            transactionHistory.add(new Transaction("Initial Deposit", initialBalance));
        }
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Core Operations

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.printf("Deposited %.2f. New balance: %.2f%n", amount, balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.printf("Withdrew %.2f. New balance: %.2f%n", amount, balance);
            return true;
        } else {
            System.out.printf("Insufficient funds. Current balance: %.2f%n", balance);
            return false;
        }
    }

    public void viewTransactionHistory() {
        System.out.printf("\n--- Transaction History for Account %s (%s) ---%n", accountNumber, accountHolderName);
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : transactionHistory) {
                System.out.println(t);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Account No: %s, Holder: %s, Balance: %.2f",
                accountNumber, accountHolderName, balance);
    }
}
