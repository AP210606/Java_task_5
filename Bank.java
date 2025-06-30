package Task_5;

import java.util.*;

public class Bank {
    private List<Account> accounts;
    private Scanner scanner;

    public Bank() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
        addSampleAccounts();
    }

    private void addSampleAccounts() {
        accounts.add(new Account("John Doe", 500.00));
        accounts.add(new SavingsAccount("Jane Smith", 1200.00, 0.015));
        System.out.println("\n--- Sample accounts created ---");
        viewAllAccounts();
    }

    // Bank Operations
    public void createAccount() {
        System.out.println("\n--- Create New Account ---");
        scanner.nextLine();

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        double initialBalance = 0;
        while (true) {
            System.out.print("Enter initial balance: ");
            try {
                initialBalance = scanner.nextDouble();
                if (initialBalance < 0) {
                    System.out.println("Initial balance cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for balance.");
                scanner.nextLine();
            }
        }

        int accountTypeChoice = -1;
        while(true) {
            System.out.print("Choose account type (1 for Standard, 2 for Savings): ");
            try {
                accountTypeChoice = scanner.nextInt();
                if (accountTypeChoice == 1 || accountTypeChoice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        if (accountTypeChoice == 1) {
            accounts.add(new Account(name, initialBalance));
        } else {
            double interestRate = 0;
            while(true) {
                System.out.print("Enter interest rate (e.g., 0.015 for 1.5%): ");
                try {
                    interestRate = scanner.nextDouble();
                    if (interestRate < 0) {
                        System.out.println("Interest rate cannot be negative. Please try again.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a numeric value for interest rate.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            accounts.add(new SavingsAccount(name, initialBalance, interestRate));
        }
    }

    public void performDeposit() {
        System.out.println("\n--- Deposit Funds ---");
        Account targetAccount = getAccountFromUser();
        if (targetAccount == null) return;

        double amount = -1;
        while(true) {
            System.out.print("Enter amount to deposit: ");
            try {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Deposit amount must be positive. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); 
            }
        }
        scanner.nextLine();
        targetAccount.deposit(amount);
    }

    public void performWithdrawal() {
        System.out.println("\n--- Withdraw Funds ---");
        Account targetAccount = getAccountFromUser();
        if (targetAccount == null) return;

        double amount = -1;
        while(true) {
            System.out.print("Enter amount to withdraw: ");
            try {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Withdrawal amount must be positive. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        targetAccount.withdraw(amount);
    }

    public void viewAccountDetails() {
        System.out.println("\n--- View Account Details ---");
        Account targetAccount = getAccountFromUser();
        if (targetAccount == null) return;

        System.out.println(targetAccount);
        targetAccount.viewTransactionHistory();
    }

    public void viewAllAccounts() {
        System.out.println("\n--- All Bank Accounts ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the bank yet.");
        } else {
            for (Account acc : accounts) {
                System.out.println(acc);
            }
        }
    }

    public void applyInterestToSavingsAccounts() {
        System.out.println("\n--- Applying Interest to Savings Accounts ---");
        boolean applied = false;
        for (Account acc : accounts) {
            if (acc instanceof SavingsAccount) {
                ((SavingsAccount) acc).applyInterest();
                applied = true;
            }
        }
        if (!applied) {
            System.out.println("No Savings Accounts found to apply interest.");
        }
    }

    private Account getAccountFromUser() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the bank. Please create one first.");
            return null;
        }
        viewAllAccounts();
        System.out.print("Enter account number: ");
        scanner.nextLine();

        String accNum = scanner.nextLine();
        Account foundAccount = findAccountByNumber(accNum);
        if (foundAccount == null) {
            System.out.println("Account with number " + accNum + " not found.");
        }
        return foundAccount;
    }

    private Account findAccountByNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    // Main Application Loop and Menu
    public void run() {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: createAccount(); break;
                    case 2: performDeposit(); break;
                    case 3: performWithdrawal(); break;
                    case 4: viewAccountDetails(); break;
                    case 5: viewAllAccounts(); break;
                    case 6: applyInterestToSavingsAccounts(); break;
                    case 7:
                        System.out.println("Exiting Bank Account Simulation. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                choice = 0;
            }
        } while (choice != 7);

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Bank Account Simulation Menu ---");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Funds");
        System.out.println("3. Withdraw Funds");
        System.out.println("4. View Account Details (and History)");
        System.out.println("5. View All Accounts");
        System.out.println("6. Apply Interest (Savings Accounts Only)");
        System.out.println("7. Exit");
        System.out.println("------------------------------------");
    }

    // Main method to start the application
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.run();
    }
}