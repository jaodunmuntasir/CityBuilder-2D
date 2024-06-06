package ui;

import features.Economy;
import features.Loan;
import features.LoanManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoanPanelTest {

    private LoanPanel loanPanel;
    private Economy economy;
    private LoanManager loanManager;

    @BeforeEach
    public void setUp() {
        economy = new Economy(10000);
        loanManager = new LoanManager(5.0, 10000);
        loanPanel = new LoanPanel(economy, loanManager);
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(loanPanel);
        assertEquals("Total Loan Balance: $0", loanPanel.currentLoanBalanceLabel.getText());
        assertEquals(5.0, loanManager.getInterestRate());
    }

    @Test
    public void testApplyLoan() {
        JTextField loanAmountField = loanPanel.loanAmountField;
        loanAmountField.setText("5000");

        JButton applyLoanButton = loanPanel.applyLoanButton;
        applyLoanButton.doClick();

        assertEquals(5000, loanManager.getTotalLoanBalance());
        assertEquals("Total Loan Balance: $5000", loanPanel.currentLoanBalanceLabel.getText());
    }

    @Test
    public void testRepayLoan() {
        Loan loan = loanManager.issueLoan(5000, economy);
        loanPanel.refreshLoanList();

        JList<Loan> loanList = loanPanel.loanList;
        loanList.setSelectedIndex(0);

        JTextField loanAmountField = loanPanel.loanAmountField;
        loanAmountField.setText("2000");

        JButton repayLoanButton = loanPanel.repayLoanButton;
        repayLoanButton.doClick();

        assertEquals(3000, loan.getBalance());
        assertEquals("Total Loan Balance: $3000", loanPanel.currentLoanBalanceLabel.getText());
    }

    @Test
    public void testRepayLoanInvalidAmount() {
        Loan loan = loanManager.issueLoan(5000, economy);
        loanPanel.refreshLoanList();

        JList<Loan> loanList = loanPanel.loanList;
        loanList.setSelectedIndex(0);

        JTextField loanAmountField = loanPanel.loanAmountField;
        loanAmountField.setText("6000");

        JButton repayLoanButton = loanPanel.repayLoanButton;

        assertThrows(IllegalArgumentException.class, () -> {
            repayLoanButton.doClick();
        });

        assertEquals(5000, loan.getBalance()); // Repayment should fail, so balance remains the same
        // assertEquals("Total Loan Balance: $5000",
        // loanPanel.currentLoanBalanceLabel.getText());
    }

    @Test
    public void testLoanListDisplay() {
        loanManager.issueLoan(5000, economy);
        loanManager.issueLoan(3000, economy);
        loanPanel.refreshLoanList();

        JList<Loan> loanList = loanPanel.loanList;
        ListModel<Loan> model = loanList.getModel();

        assertEquals(2, model.getSize());
        assertEquals(5000, model.getElementAt(0).getPrincipal());
        assertEquals(3000, model.getElementAt(1).getPrincipal());
    }
}