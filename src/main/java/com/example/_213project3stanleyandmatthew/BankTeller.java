package com.example._213project3stanleyandmatthew;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This BankTeller class holds an AccountDatabase and acts as a way to interact with it using the terminal.
 * the necessary commands each have their own private methods and the run method
 * initializes the AccountDatabase and starts its operation.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */

public class BankTeller {
    private AccountDatabase accountDatabase;
    private static final boolean OPEN = false;
    private static final int DEFAULTSCHOOL = 1;
    private static final int DEFAULTLOYALTY = 1;
    private static final int DEFAULTBALANCE = 1;
    private static final int MAX_VALID_SCHOOL = 2;
    private static final int MIN_MM_DEPOSIT = 2500;
    private static final int MIN_VALID_TOKENS = 4;

    /**
     * creates the bankteller and continuously polls for specific commands
     * to be input via the command line to the kiosk, and routes the
     * parameters for each command to their respective private methods and exception handlers.
     * returns and terminates when the Q command is input
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        this.accountDatabase = new AccountDatabase();
        System.out.println("Bank Teller is running.");
        System.out.println();
        String currentLine = scanner.nextLine();
        StringTokenizer currentString = new StringTokenizer(currentLine);
        String command = currentString.nextToken();
        while (!command.equals("Q")) {
            if (command.equals("O")) {
                O(currentString);
            } else if (command.equals("C")) {
                C(currentString);
            } else if (command.equals("D")) {
                D(currentString);
            } else if (command.equals("W")) {
                W(currentString);
            } else if (command.equals("P")) {
                P();
            } else if (command.equals("PT")) {
                PT();
            } else if (command.equals("PI")) {
                PI();
            } else if (command.equals("UB")) {
                UB();
            } else {
                System.out.println("Invalid command!");
            }
            currentLine = scanner.nextLine();
            while (currentLine.length() == 0) {
                currentLine = scanner.nextLine();
            }
            currentString = new StringTokenizer(currentLine);
            command = currentString.nextToken();
        }
        System.out.println("Bank Teller is terminated.");
        return;
    }

    /**
     * Prints all the accounts in the AccountDatabase in their current order
     * by calling the print method in the AccountDatabase class.
     */
    private void P() {
        if (this.accountDatabase.getNumAccounts() == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts in the database*");
        accountDatabase.print();
        System.out.println("*end of list*");
        System.out.println();
        return;
    }

    /**
     * Prints all the accounts in the AccountDatabase sorted by account type
     * by calling the printByAccountType method in the AccountDatabase class.
     */
    private void PT() {
        if (this.accountDatabase.getNumAccounts() == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts by account type.");
        accountDatabase.printByAccountType();
        System.out.println("*end of list.");
        System.out.println();
        return;
    }

    /**
     * Prints all the accounts in the AccountDatabase sorted by account type
     * by calling the printFeeAndInterest method in the AccountDatabase class.
     */
    private void PI() {
        if (this.accountDatabase.getNumAccounts() == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts with fee and monthly interest");
        accountDatabase.printFeeAndInterest();
        System.out.println("*end of list.");
        System.out.println();
        return;
    }

    /**
     * Updates the balances of all the accounts in the AccountDatabase with their monthly interest rate and fee
     * by calling the updateBalances method in the AccountDatabase class.
     */
    private void UB() {
        if (this.accountDatabase.getNumAccounts() == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts with updated balance");
        this.accountDatabase.updateBalances();
        System.out.println("*end of list.");
        System.out.println();
        return;
    }

    /**
     * Takes a String withdrawal amount from the W command and checks if the withdrawAmount is valid
     *
     * @param withdrawAmount: the string with the supposed withdrawAmount for the withdraw call
     * @return true if the withdrawAmount is valid, false if it fails any of the checks.
     */
    private static boolean isDoubleAndExcepWith(String withdrawAmount) {
        try {
            Double.parseDouble(withdrawAmount);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        if (Double.parseDouble(withdrawAmount) <= 0) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Takes a String Tokenizer of the parameters needed to withdraw from a specific account
     * the parameters are loaded form the command line and then checked  in isDoubleAndExcepWith() to ensure
     * they are valid parameters, once all checks are passed the withdraw method
     * from the AccountDatabase class is called to withdraw the specified amount from the account
     *
     * @param parameters: the string tokenized command line input with W
     */
    private void W(StringTokenizer parameters) {
        String type = parameters.nextToken();
        String fname = parameters.nextToken();
        String lname = parameters.nextToken();
        String date = parameters.nextToken();
        Profile profile = new Profile(fname, lname, date);
        String withdrawAmount = parameters.nextToken();
        Account account;
        boolean isDouble = isDoubleAndExcepWith(withdrawAmount);
        if (isDouble == false) {
            return;
        }
        if (type.equals("C")) {
            account = new Checking(profile, OPEN, Double.parseDouble(withdrawAmount));
        } else if (type.equals("CC")) {
            account = new CollegeChecking(profile, OPEN, Double.parseDouble(withdrawAmount), DEFAULTSCHOOL);
        } else if (type.equals("MM")) {
            account = new MoneyMarket(profile, OPEN, Double.parseDouble(withdrawAmount));
        } else { //must equal S if it reaches here
            account = new Savings(profile, OPEN, Double.parseDouble(withdrawAmount), DEFAULTLOYALTY);
        }

        boolean withdrawIsValid;

        boolean successfulWithdraw = this.accountDatabase.withdraw(account);
        if (successfulWithdraw) {
            System.out.println("Withdraw - balance updated.");
        } else if (accountDatabase.accountExists(account)) {
            System.out.println("Withdraw - insufficient fund.");
        } else {
            System.out.println(profile.toString() + " " + account.getType() + " is not in the database.");
        }
        return;
    }

    /**
     * Takes a String Tokenizer of the parameters needed to close a specific account
     * the parameters are loaded form the command line and then checked to ensure
     * they are valid parameters. Then the close() method
     * from the AccountDatabase class is called to close the specified account
     *
     * @param parameters: the string tokenized command line input with C
     */
    private void C(StringTokenizer parameters) {

        if (parameters.countTokens() < MIN_VALID_TOKENS) { // check if there were enough parameters input
            System.out.println("Missing data for closing an account.");
            return;
        }
        String type = parameters.nextToken();
        String fname = parameters.nextToken();
        String lname = parameters.nextToken();
        String date = parameters.nextToken();
        Profile profile = new Profile(fname, lname, date);
        Account account;
        if (type.equals("C")) {
            account = new Checking(profile, OPEN, DEFAULTBALANCE);
        } else if (type.equals("CC")) {
            account = new CollegeChecking(profile, OPEN, DEFAULTBALANCE, DEFAULTSCHOOL);
        } else if (type.equals("MM")) {
            account = new MoneyMarket(profile, OPEN, DEFAULTBALANCE);
        } else { //must equal S if it reaches here
            account = new Savings(profile, OPEN, DEFAULTBALANCE, DEFAULTLOYALTY);
        }
        boolean accountClosed = accountDatabase.close(account);
        if (accountClosed) {
            System.out.println("Account closed.");
        } else {
            System.out.println("Account is closed already.");
        }
        return;
    }

    /**
     * Takes a String depositAmount from the D command and checks if the depositAmount is valid
     *
     * @param depositAmount: the string with the supposed depositAmount for the deposit call
     * @return true if the depositAmount is valid, false if it fails any of the checks.
     */
    private static boolean isDoubleAndExcepDep(String depositAmount) {
        try {
            Double.parseDouble(depositAmount);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        if (Double.parseDouble(depositAmount) <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Takes a String Tokenizer of the parameters needed to deposit to a specific account
     * the parameters are loaded form the command line and then checked  in isDoubleAndExcepDep() to ensure
     * they are valid parameters, once all checks are passed the deposit method
     * from the AccountDatabase class is called to deposit the specified amount to the account
     *
     * @param parameters: the string tokenized command line input with D
     */
    private void D(StringTokenizer parameters) {
        String type = parameters.nextToken();
        String fname = parameters.nextToken();
        String lname = parameters.nextToken();
        String date = parameters.nextToken();
        Profile profile = new Profile(fname, lname, date);
        String depositAmount = parameters.nextToken();
        Account account;
        boolean isDouble = isDoubleAndExcepDep(depositAmount);
        if (isDouble == false) {
            return;
        }
        if (type.equals("C")) {
            account = new Checking(profile, OPEN, Double.parseDouble(depositAmount));
        } else if (type.equals("CC")) {
            account = new CollegeChecking(profile, OPEN, Double.parseDouble(depositAmount), DEFAULTSCHOOL);
        } else if (type.equals("MM")) {
            account = new MoneyMarket(profile, OPEN, Double.parseDouble(depositAmount));
        } else { //must equal S if it reaches here
            account = new Savings(profile, OPEN, Double.parseDouble(depositAmount), DEFAULTLOYALTY);
        }
        boolean deposit = this.accountDatabase.isValidDeposit(account);
        if (deposit) {
            System.out.println("Deposit - balance updated.");
        } else {
            System.out.println(profile.toString() + " " + account.getType() + " is not in the database.");
        }

        return;
    }

    /**
     * Takes a String parameters and checks if these are valid parameters to open an account with
     *
     * @param parameters: the string tokenized command line input with D that needs to be checked
     * @return true if account parameters are valid, false if they fail any of the checks.
     */
    private static boolean OParamExceptions(StringTokenizer parameters) {
        try {
            if (parameters.countTokens() < 5) { // check if there were enough parameters input
                System.out.println("Missing data for opening an account.");
                return false;
            }
            if (parameters == null) {
                System.out.println("Missing data for opening an account.");
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Missing data for opening an account.");
            return false;
        }
        return true; //valid account to open if input here
    }

    /**
     * Takes a String initialAmount from the O command and checks if the initialAmount is valid to open an
     * account with.
     *
     * @param initialAmount: the string with the supposed initialAmount for the account to open
     * @return true if the initialAmount is valid, false if it fails any of the checks.
     */
    private static boolean isDoubleAndExcepOp(String initialAmount) {
        try {
            Double.parseDouble(initialAmount);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        if (Double.parseDouble(initialAmount) <= 0) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Opens or reopens account in accountDatabase if no account of same type exists.
     *
     * @param account: Account to be opened in the accountDatabase.
     */
    private void validateOpening(Account account) {
        int accountNum = this.accountDatabase.getNumAccounts();
        boolean opened = this.accountDatabase.open(account);
        if (opened) {
            if (accountNum != this.accountDatabase.getNumAccounts()) {
                System.out.println("Account opened.");
            } else {
                System.out.println("Account reopened.");
            }
        } else {
            System.out.println(account.holder.toString() + " same account(type) is in the database.");
        }
    }

    /**
     * Checks if school number is valid (in range 0-2)
     *
     * @param school: school number to be validated.
     * @return true if school number is valid, otherwise false.
     */
    private boolean hasValidSchool(int school) {
        if (school > MAX_VALID_SCHOOL) {
            System.out.println("Invalid campus code.");
            return false;
        }

        return true;
    }

    /**
     * Checks if profile has a valid DOB.
     *
     * @param profile: profile to be validated.
     * @return true if profile has a valid DOB, otherwise false.
     */
    private boolean profileIsValid(Profile profile) {
        if (!profile.hasValidDOB()) {
            System.out.println("Date of birth invalid.");
            return false;
        }

        return true;
    }

    /**
     * Checks if the initial deposit to an MoneyMarket Account is valid ( > $2500)
     *
     * @param initialDeposit: Initial deposit to be validated.
     * @return true if initialDeposit is valid, otherwise false.
     */
    private boolean mmIsValid(Double initialDeposit) {
        if (initialDeposit < MIN_MM_DEPOSIT) {
            System.out.println("Minimum of $2500 to open a MoneyMarket account.");
            return false;
        }

        return true;
    }

    /**
     * Takes a String Tokenizer of the parameters needed to open a new account
     * the parameters are loaded form the command line and then checked  in isDoubleAndExcepOp() and
     * OParamExceptions() to ensure they are valid parameters to open an account with,
     * once all checks are passed the open method from the AccountDatabase class is called to open
     * an account with the specified parameters in the AccountDatabase.
     *
     * @param parameters: the string tokenized command line input with O
     */
    private void O(StringTokenizer parameters) {
        if (!OParamExceptions(parameters)) { // check if there were enough parameters input
            return;
        }
        String type = parameters.nextToken();
        Profile profile = new Profile(parameters.nextToken(), parameters.nextToken(), parameters.nextToken());
        String initialBalance = parameters.nextToken();
        if (!isDoubleAndExcepOp(initialBalance) || !profileIsValid(profile)) {
            return;
        }

        Double balance = Double.parseDouble(initialBalance);
        Account account;
        if (type.equals("C")) {
            account = new Checking(profile, OPEN, balance);
        } else if (type.equals("CC")) {
            int school = Integer.parseInt(parameters.nextToken());
            account = hasValidSchool(school) ? new CollegeChecking(profile, OPEN, balance, school) : null;
        } else if (type.equals("MM")) {
            account = mmIsValid(balance) ? new MoneyMarket(profile, OPEN, balance) : null;
        } else { //must equal S if it reaches here
            int loyalty = Integer.parseInt(parameters.nextToken());
            account = new Savings(profile, OPEN, balance, loyalty);
        }

        if (account != null) {
            validateOpening(account);
        }
        return;
    }
}
