package features;

import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private List<Loan> loans;
    private double interestRate;
    private int maxLoanAmount;

    public LoanManager(double interestRate, int maxLoanAmount) {
        this.interestRate = interestRate;
        this.maxLoanAmount = maxLoanAmount;
        this.loans = new ArrayList<>();
    }

    public Loan issueLoan(int amount, Economy economy) {
        if (amount > maxLoanAmount) {
            throw new IllegalArgumentException("Loan amount exceeds maximum limit.");
        }
        Loan loan = new Loan(amount, interestRate);
        loans.add(loan);
        economy.earn(amount); // Add the loan amount to the economy after creating the loan
        return loan;
    }

    public void repayLoan(Loan loan, int amount, Economy economy) {
        if (amount > loan.getBalance()) {
            throw new IllegalArgumentException("Repayment amount exceeds loan balance.");
        }
        economy.spend(amount);
        loan.repay(amount);
        if (loan.isPaidOff()) {
            loans.remove(loan);
        }
    }

    public void updateLoans() {
        for (Loan loan : loans) {
            loan.accrueInterest();
        }
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public int getTotalLoanBalance() {
        return loans.stream().mapToInt(Loan::getBalance).sum();
    }
}