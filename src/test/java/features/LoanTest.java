package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private Loan loan;

    @BeforeEach
    public void setUp() {
        loan = new Loan(1000, 5.0); // Principal: 1000, Interest Rate: 5.0%
    }

    @Test
    public void testInitialValues() {
        assertEquals(1000, loan.getPrincipal());
        assertEquals(5.0, loan.getInterestRate());
        assertEquals(1000, loan.getBalance());
        assertFalse(loan.isPaidOff());
    }

    @Test
    public void testAccrueInterest() {
        loan.accrueInterest();
        assertEquals(1050, loan.getBalance()); // 1000 + 5% interest

        loan.accrueInterest();
        assertEquals(1102.0, loan.getBalance(), 0.01); // 1050 + 5% interest
    }

    @Test
    public void testRepay() {
        loan.repay(200);
        assertEquals(800, loan.getBalance());
        assertFalse(loan.isPaidOff());

        loan.repay(800);
        assertEquals(0, loan.getBalance());
        assertTrue(loan.isPaidOff());
    }

    @Test
    public void testRepayMoreThanBalance() {
        loan.repay(1200);
        assertEquals(0, loan.getBalance());
        assertTrue(loan.isPaidOff());
    }

    @Test
    public void testToString() {
        String expected = "Loan Amount: $1000, Balance: $1000, Paid Off: false";
        assertEquals(expected, loan.toString());

        loan.repay(1000);
        expected = "Loan Amount: $1000, Balance: $0, Paid Off: true";
        assertEquals(expected, loan.toString());
    }
}
