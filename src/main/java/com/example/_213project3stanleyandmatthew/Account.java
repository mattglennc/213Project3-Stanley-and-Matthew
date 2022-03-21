package com.example._213project3stanleyandmatthew;

import java.text.DecimalFormat;

/**
 * This abstract Account class provides the data fields, constructors and methods to be inherited
 * and implemented by subclasses.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public abstract class Account {

    protected final static int MONTHS_IN_YEAR = 12; //Constant for the number of months in a year.

    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * Creates Account object, using parameters to fill necessary data fields.
     *
     * @param holder  -  Profile which holds the account.
     * @param closed  -  Boolean representation of account status.
     * @param balance - Initial account balance.
     */
    public Account(Profile holder, boolean closed, double balance) {
        this.holder = holder;
        this.closed = closed;
        this.balance = balance;
    }

    /**
     * Returns the balance of this account.
     *
     * @return the balance of this account as a double.
     */
    public double getBalance() {
        return this.balance;
    }


    /**
     * Takes an object and determines if it is equal to this Account.  Accounts are equal
     * if they have same holder and type.
     *
     * @param obj - object to compare with this Account.
     * @return - true if accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account a = (Account) obj;
            return a.holder.equals(this.holder);
        }
        return false;
    }

    /**
     * Gives String representation of Account in the following format:
     * "[type]::[holder]::Balance [balance]" with "::CLOSED" added if Account is closed.
     *
     * @return String representation of Account.
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String result = (this.getType() + "::" + this.holder.toString() + "::Balance $" + decimalFormat.format(this.balance));
        if (closed) {
            return (result + "::CLOSED");
        }
        return result;
    }

    /**
     * Gives the account's closed status in boolean form.
     *
     * @return true if closed, false otherwise.
     */
    public boolean isClosed() {
        return this.closed;
    }

    /**
     * Closes the account, setting closed to true.
     */
    public void setClosed() {
        this.closed = true;
        this.balance = 0.00;
    }

    /**
     * reOpens the account, setting closed to false.
     */
    public void reOpen() {
        this.closed = false;
    }

    /**
     * Withdraws amount from balance.
     *
     * @param amount - the double amount to be withdrawn from the account.
     */
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    /**
     * Deposits amount to balance.
     *
     * @param amount - the double amount to be deposited the account.
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Abstract method that calculates and returns the profile's monthly interest rate.
     *
     * @return Monthly interest rate of profile.
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that determines and returns the profile's monthly fee based on if
     * profile balance exceeds satisfactory balance amount.
     *
     * @return Monthly fee of profile.
     */
    public abstract double fee(); //return the monthly fee

    /**
     * Abstract method which string representation of class's type.
     *
     * @return string representation of class type.
     */
    public abstract String getType(); //return the account type (class name)

}