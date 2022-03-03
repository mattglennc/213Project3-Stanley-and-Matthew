package com.example._213project3stanleyandmatthew.accounting;

/**
 * This MoneyMarket class provides the constructors and methods for the MoneyMarket object which
 * extends from the Savings class and inherits its instance variables and methods.
 * The class includes the withdrawals instance variable, which is an integer
 * representation of the account's amount of withdrawals.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public class MoneyMarket extends Savings {

    private static final double ANNUAL_INTEREST_RATE = 0.008;
    private static final double LOYAL_ANNUAL_INTEREST_RATE = 0.0095;
    private static final double MONTHLY_FEE = 10.00;
    private static final double MONTHLY_FEE_WAIVER = 2500.00;
    private static final int MAX_WITHDRAWS = 3;


    private int withdrawals; //Integer indicating amount of withdrawals the profile has.

    /**
     * Creates MoneyMarket object, and invokes the superclass constructor.
     *
     * @param holder  -  Profile which holds the account, inherited from Account class.
     * @param closed  -  Boolean representation of account status, inherited from Account class.
     * @param balance - Initial account balance, inherited from Account class.
     */
    public MoneyMarket(Profile holder, boolean closed, double balance) {
        super(holder, closed, balance, 1);
        this.withdrawals = 0;
    }

    /**
     * Calculates and returns the profile's monthly interest rate, implemented from the abstract
     * Account to the Savings class, based on the loyalty status of the profile.
     *
     * @return Monthly interest rate of profile.
     */
    public double monthlyInterest() {
        if (loyalty) {
            return (super.balance * LOYAL_ANNUAL_INTEREST_RATE) / MONTHS_IN_YEAR;
        }
        return (super.balance * ANNUAL_INTEREST_RATE) / MONTHS_IN_YEAR;
    }

    /**
     * Determines and returns the profile's monthly fee based on number of withdrawals
     * and balance amount, implemented from the abstract Account class to the Savings class.
     *
     * @return Monthly fee of profile.
     */
    public double fee() {
        if (super.balance >= MONTHLY_FEE_WAIVER || withdrawals > MAX_WITHDRAWS) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }
    }

    /**
     * Returns string representation of class's type, implemented from abstract Account class to the
     * savings class.
     *
     * @return string representation of class type.
     */
    public String getType() {
        return ("Money Market Savings");
    }

    /**
     * Withdraws amount from balance, inherited from superclass. If the balance goes below the
     * threshold, the profile is no longer loyal.
     *
     * @param amount - the Double amount to be withdrawn from the account.
     */
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        this.withdrawals++;
        if (super.balance < MONTHLY_FEE_WAIVER) {
            super.loyalty = false;
        }
    }

    /**
     * Resets the MoneyMarket's number of withdrawals back to 0.
     */
    public void resetWithdrawals() {
        this.withdrawals = 0;
    }

    /**
     * Deposits amount to balance, inherited from superclass. If the balance goes above the
     * threshold, the account becomes loyal.
     *
     * @param amount - the Double amount to be deposited the account.
     */
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        if (super.balance >= MONTHLY_FEE_WAIVER) {
            super.loyalty = true;
        }
    }

    /**
     * Takes an object and determines if it is equal to this MoneyMarket.  MoneyMarkets are equal
     * if they have same type and holder.
     *
     * @param obj - object to compare with this MoneyMarket.
     * @return - true if MoneyMarkets are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoneyMarket) {
            MoneyMarket mm = (MoneyMarket) obj;
            return this.holder.equals(mm.holder) && this.getType().equals(mm.getType());
        }
        return false;
    }

    /**
     * Returns String representation of MoneyMarket profile, inherited from the superclass, as well
     * as an indication of the profile's loyalty status.
     *
     * @return String representation of the MoneyMarket object, with indication of loyalty.
     */
    @Override
    public String toString() {
        String result = super.toString();
        return (result + "::withdrawl: " + this.withdrawals);
    }


}