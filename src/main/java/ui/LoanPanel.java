package ui;

import features.Economy;
import features.LoanManager;
import features.Loan;

import javax.swing.*;
import java.awt.*;

public class LoanPanel extends JPanel {
    JLabel currentLoanBalanceLabel;
    private JLabel loanAmountLabel;
    private JLabel interestRateLabel;
    JButton applyLoanButton;
    JButton repayLoanButton;
    JTextField loanAmountField;
    private Economy economy;
    private LoanManager loanManager;
    JList<Loan> loanList;
    private DefaultListModel<Loan> loanListModel;

    public LoanPanel(Economy economy, LoanManager loanManager) {
        this.economy = economy;
        this.loanManager = loanManager;
        setupUI();
        refreshLoanList(); // Initial refresh of the loan list
    }

    private void setupUI() {
        setLayout(new BorderLayout(11, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        currentLoanBalanceLabel = new JLabel("Total Loan Balance: $" + loanManager.getTotalLoanBalance());
        currentLoanBalanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(currentLoanBalanceLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        loanAmountLabel = new JLabel("Loan Amount:");
        loanAmountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(loanAmountLabel, gbc);

        loanAmountField = new JTextField(10);
        loanAmountField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(loanAmountField, gbc);

        interestRateLabel = new JLabel("Interest Rate: " + loanManager.getInterestRate() + "%");
        interestRateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(interestRateLabel, gbc);

        applyLoanButton = new JButton("Apply for Loan");
        applyLoanButton.setFont(new Font("Arial", Font.PLAIN, 14));
        applyLoanButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(loanAmountField.getText());
                loanManager.issueLoan(amount, economy);
                refreshLoanList();
                currentLoanBalanceLabel.setText("Total Loan Balance: $" + loanManager.getTotalLoanBalance());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(applyLoanButton, gbc);

        repayLoanButton = new JButton("Repay Loan");
        repayLoanButton.setFont(new Font("Arial", Font.PLAIN, 14));
        repayLoanButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(loanAmountField.getText());
                Loan selectedLoan = loanList.getSelectedValue();
                if (selectedLoan != null) {
                    loanManager.repayLoan(selectedLoan, amount, economy);
                    refreshLoanList();
                    currentLoanBalanceLabel.setText("Total Loan Balance: $" + loanManager.getTotalLoanBalance());
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a loan to repay.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        });
        gbc.gridx = 1;
        formPanel.add(repayLoanButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        loanListModel = new DefaultListModel<>();
        loanList = new JList<>(loanListModel);
        loanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loanList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(loanList);
        scrollPane.setPreferredSize(new Dimension(250, 150));
        add(scrollPane, BorderLayout.EAST);
    }

    void refreshLoanList() {
        loanListModel.removeAllElements();
        for (Loan loan : loanManager.getLoans()) {
            loanListModel.addElement(loan);
        }
    }
}