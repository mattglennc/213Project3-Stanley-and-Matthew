package com.example._213project3stanleyandmatthew;

import java.text.DecimalFormat;

/**
 * This Schedule class provides the methods and constructors for the AccountDatabase object.
 * The AccountDatabase constructor takes nothing and creates an AccountDatabase object that holds accounts
 * The find method finds an account in the AccountDatabase
 * the close method closes an account in the AccountDatabase
 * the open method opens an account in the AccountDatabase
 * The print methods print the appointments in the AccountDatabase in specified configurations
 *
 * @author Matthew Carrascoso & Stanley Chou
 */

public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;
    public static final int NOT_FOUND = -1;
    private static final int INITIAL_SIZE = 4;
    private static final int GROWTH_FACTOR = 4;

    /**
     * Creates an account database object container of initial_Size of 4 and numAcct 0
     */
    public AccountDatabase() {
        this.accounts = new Account[INITIAL_SIZE];
        this.numAcct = 0;
    }

    /**
     * Takes an account and returns its index in the AccountDatabase, returns NOT_FOUND if not found.
     *
     * @param account - the account that needs to be found in the AccountDatabase returning its index
     */
    private int find(Account account) {
        for (int i = 0; i < this.accounts.length; i++) {
            if (account.equals(this.accounts[i])) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Looks for an account of the same instance and returns index in the AccountDatabase, returning NOT_FOUND
     * if not found.
     *
     * @param account - the account whose type needs to be found in the AccountDatabase returning its index.
     * @return index of found account, NOT_FOUND otherwise.
     */
    private int findThisType(Account account) {
        for (int i = 0; this.accounts[i] != null; i++) {
            boolean sameTypeChecking = account instanceof Checking && this.accounts[i] instanceof Checking;
            boolean sameTypeSavings = account instanceof Savings && this.accounts[i] instanceof Savings;
            if (account.holder.equals(this.accounts[i].holder) && (sameTypeChecking || sameTypeSavings)) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Creates an AccountDatabase object of capacity of the AccountDatabase + 4 and then copies over that values of that
     * AccountDatabase into the new array and sets it as appointments.
     */
    private void grow() {
        Account[] newAccounts = new Account[this.accounts.length + GROWTH_FACTOR];
        for (int i = 0; i < this.accounts.length; i++) {
            newAccounts[i] = this.accounts[i];
        }
        this.accounts = newAccounts;
    }

    /**
     * If an the taken Account being reopened is CollegeChecking, reset its school as needed.
     *
     * @param account: account to be checked.
     */
    private void resetCollege(Account account) {
        if (account instanceof CollegeChecking) {
            CollegeChecking newCollege = (CollegeChecking) account;
            CollegeChecking oldCollege = (CollegeChecking) this.accounts[find(account)];
            oldCollege.setSchool(newCollege.getSchool());
            this.accounts[find(account)] = oldCollege;
        }
    }

    /**
     * Takes an account and adds it to the AccountDatabase if it is not already in the AccountDatabase.
     *
     * @param account - the account that needs to be added to the AccountDatabase
     * @return true if the account is added to the AccountDatabase, false if the account already exists
     */
    public boolean open(Account account) {
        if (this.numAcct == this.accounts.length) {
            grow();
        }
        if (find(account) != NOT_FOUND) {
            if (this.accounts[find(account)].isClosed()) {
                this.accounts[find(account)].reOpen();
                this.accounts[find(account)].balance = account.balance;
                resetCollege(account);
                return true;
            }
            return false;
        } else {
            if (findThisType(account) != NOT_FOUND) {
                if (account instanceof Checking) {
                    return false;
                }
            }
        }
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                this.accounts[i] = account;
                this.numAcct = this.numAcct + 1;
                return true;
            }
        }
        return false;
    }

    /**
     * Takes an account and closes it in the AccountDatabase if it is in the AccountDatabase.
     *
     * @param account - the account that needs to be closed in the AccountDatabase
     * @return false if the account is already closed in the AccountDatabase, true if the account closing is successful
     */
    public boolean close(Account account) {
        int closeIndex = find(account);

        if (closeIndex == NOT_FOUND) {
            return false;
        }

        if (this.accounts[closeIndex].isClosed()) {
            return false;
        } else {
            this.accounts[closeIndex].setClosed();
            if (this.accounts[closeIndex] instanceof MoneyMarket) {
                ((MoneyMarket) this.accounts[closeIndex]).resetWithdrawals();
            }
            return true;
        }
    }

    /**
     * Takes an account and deposits money into it if it is found to be a valid account.
     *
     * @param account - the account that needs to be deposited into in the AccountDatabase
     * @return true if deposit is valid, false if account cannot be found
     */
    public boolean isValidDeposit(Account account) {
        int accountIndex = find(account);
        double depositAmount = account.balance;
        if (accountIndex == NOT_FOUND) {
            return false;
        }

        if (this.accounts[find(account)].getType() != account.getType()) {
            return false;
        }
        deposit(account);
        return true;
    }

    /**
     * Takes an account and deposits money into it if it is found in the AccountDatabase.
     *
     * @param account - the account that needs to be deposited into in the AccountDatabase
     */
    public void deposit(Account account) {
        int accountIndex = find(account);
        double depositAmount = account.balance;
        this.accounts[accountIndex].deposit(depositAmount);
        return;
    }

    /**
     * Updates the balances in the AccountDatabase based on their account fees and monthly interest rates .
     *
     * @return String the final formatted String of accounts to be printed on the GUI
     */
    public String updateBalances() {
        double updateAmount;
        for (int j = 0; j < this.accounts.length; j++) {
            if (this.accounts[j] == null) {
                continue;
            }
            updateAmount = this.accounts[j].monthlyInterest() - this.accounts[j].fee();
            this.accounts[j].balance = this.accounts[j].balance + updateAmount;
        }
        String finalAccountString = print();
        return finalAccountString;
    }

    /**
     * Takes an account and checks if it exists in the AccountDatabase.
     *
     * @param account - the account that needs to have its existence checked
     * @return true if account exists, false if account does not already exist
     */
    public boolean accountExists(Account account) {
        return find(account) != NOT_FOUND && this.accounts[find(account)].getType() == account.getType();
    }

    /**
     * Takes an account and withdraws money out of it if it is found in the AccountDatabase.
     *
     * @param account - the account that needs money withdrawn from it in the AccountDatabase
     * @return true if withdrawal is successful, false if account cannot be found or withdrawal amount is invalid
     */
    public boolean withdraw(Account account) {
        int accountIndex = find(account);
        double withdrawalAmount = account.balance;
        if (accountIndex == NOT_FOUND) {
            return false;
        }

        if (this.accounts[find(account)].getType() != account.getType()) {
            return false;
        }

        if (this.accounts[accountIndex].balance >= withdrawalAmount) {
            this.accounts[accountIndex].withdraw(withdrawalAmount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * prints all the accounts in the AccountDatabase in their current order.
     *
     * @return String the final formatted String of accounts to be printed on the GUI
     */
    public String print() {
        String accountsString = "";
        for (int i = 0; i < this.numAcct; i++) {
            accountsString = accountsString + this.accounts[i].toString() + " \n ";
        }
        return accountsString;
    }

    /**
     * Gets the number of accounts in the AccountDatabase
     *
     * @return int number of accounts in the AccountDatabase
     */
    public int getNumAccounts() {
        return this.numAcct;
    }


    /**
     * prints all the accounts in the AccountDatabase in order of account type.
     *
     * @return String the final formatted String of accounts to be printed on the GUI
     */
    public String printByAccountType() {
        String accountsString = "";
        for (int i = 1; i < this.accounts.length; i++) {
            if (accounts[i] == null) {
                continue;
            }
            String accountType = accounts[i].getType();
            int j = i - 1;
            Account key = accounts[i];
            while (j >= 0 && accounts[j].getType().compareTo(accountType) > 0) { //sort by alphabetical type order
                accounts[j + 1] = accounts[j];
                j = j - 1;
            }
            accounts[j + 1] = key;
        }
        accountsString = print();
        return accountsString;
    }

    /**
     * prints all the accounts in the AccountDatabase with their monthly interest and fee included too.
     *
     * @return String the final formatted String of accounts to be printed on the GUI
     */
    public String printFeeAndInterest() {
        String finalAccountString = "";
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        for (int i = 0; i < this.numAcct; i++) {
            String accountString = this.accounts[i].toString();
            String monthlyInterest = decimalFormat.format(this.accounts[i].monthlyInterest());
            String fee = decimalFormat.format(this.accounts[i].fee());
            finalAccountString = finalAccountString + accountString + "::fee $" + fee + "::monthly interest $" + monthlyInterest + " \n ";
        }
        return finalAccountString;
    }

}