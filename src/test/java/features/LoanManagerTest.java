package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanManagerTest {

    private LoanManager loanManager;
    private Economy economy;

    @BeforeEach
    public void setUp() {
        loanManager = new LoanManager(5.0, 10000); // Interest Rate: 5.0%, Max Loan Amount: 10000
        economy = new Economy(10000); // Initial economy budget of 10000
    }

    @Test
    public void testIssueLoan() {
        Loan loan = loanManager.issueLoan(5000, economy);

        assertNotNull(loan);
        assertEquals(5000, loan.getPrincipal());
        assertEquals(5.0, loan.getInterestRate());
        assertEquals(15000, economy.getBudget()); // 10000 initial + 5000 loan amount
        assertEquals(1, loanManager.getLoans().size());
    }

    @Test
    public void testIssueLoanExceedsMax() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanManager.issueLoan(15000, economy);
        });
    }

    @Test
    public void testRepayLoan() {
        Loan loan = loanManager.issueLoan(5000, economy);
        loanManager.repayLoan(loan, 2000, economy);

        assertEquals(3000, loan.getBalance());
        assertEquals(13000, economy.getBudget()); // 15000 - 2000 repayment
        assertEquals(1, loanManager.getLoans().size());

        loanManager.repayLoan(loan, 3000, economy);
        assertEquals(0, loan.getBalance());
        assertEquals(10000, economy.getBudget()); // 13000 - 3000 repayment
        assertEquals(0, loanManager.getLoans().size());
    }

    @Test
    public void testRepayLoanExceedsBalance() {
        Loan loan = loanManager.issueLoan(5000, economy);

        assertThrows(IllegalArgumentException.class, () -> {
            loanManager.repayLoan(loan, 6000, economy);
        });
    }

    @Test
    public void testUpdateLoans() {
        Loan loan = loanManager.issueLoan(5000, economy);
        loanManager.updateLoans();

        assertEquals(5250, loan.getBalance()); // 5000 + 5% interest
    }

    @Test
    public void testGetTotalLoanBalance() {
        @SuppressWarnings("unused")
        Loan loan1 = loanManager.issueLoan(5000, economy);
        @SuppressWarnings("unused")
        Loan loan2 = loanManager.issueLoan(3000, economy);
        loanManager.updateLoans();

        assertEquals(8400, loanManager.getTotalLoanBalance()); // 5250 + 3150
    }
}
