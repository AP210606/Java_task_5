package Task_5;

public class SavingsAccount extends Account {
    private double interestRate;
    private static final double MIN_BALANCE = 100.0;

    public SavingsAccount(String accountHolderName, double initialBalance, double interestRate) {
        super(accountHolderName, initialBalance);
        this.interestRate = interestRate;
        System.out.printf("Savings Account created with interest rate: %.2f%% and minimum balance requirement: %.2f%n",
                          interestRate * 100, MIN_BALANCE);
    }

    public double getInterestRate() {
        return interestRate;
    }

    // Method Overriding: Specifying different withdrawal logic for SavingsAccount
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        // Check if withdrawal leaves balance below minimum
        if (balance - amount < MIN_BALANCE) {
            System.out.printf("Withdrawal not allowed. Minimum balance of %.2f must be maintained.%n", MIN_BALANCE);
            System.out.printf("Current balance: %.2f, Attempted withdrawal: %.2f%n", balance, amount);
            return false;
        }
        // If checks pass, call the superclass (Account) withdraw method
        return super.withdraw(amount);
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        transactionHistory.add(new Transaction("Interest Applied", interest));
        System.out.printf("Interest of %.2f applied. New balance: %.2f%n", interest, balance);
    }

    @Override
    public String toString() {
        return String.format("Savings Account No: %s, Holder: %s, Balance: %.2f, Interest Rate: %.2f%%",
                accountNumber, accountHolderName, balance, interestRate * 100);
    }
}