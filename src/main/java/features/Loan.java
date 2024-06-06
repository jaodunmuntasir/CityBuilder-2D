package features;

public class Loan {
    private int principal;
    private double interestRate;
    private int balance;
    private boolean paidOff;

    public Loan(int principal, double interestRate) {
        this.principal = principal;
        this.interestRate = interestRate;
        this.balance = principal;
        this.paidOff = false;
    }

    public void accrueInterest() {
        if (!paidOff) {
            balance += balance * interestRate / 100;
        }
    }

    public void repay(int amount) {
        balance -= amount;
        if (balance <= 0) {
            balance = 0;
            paidOff = true;
        }
    }

    public boolean isPaidOff() {
        return paidOff;
    }

    public int getBalance() {
        return balance;
    }

    public int getPrincipal() {
        return principal;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "Loan Amount: $" + principal + ", Balance: $" + getBalance() + ", Paid Off: " + isPaidOff();
    }
}