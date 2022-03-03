package com.example._213project3stanleyandmatthew.accounting;

/**
 * This Savings class provides the constructors and methods for the Savings object which
 * extends from the abstract Account class and inherits its instance variables and methods.
 * The class includes the loyalty instance variable, which is a boolean representation of
 * then account's loyalty status.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public class Savings extends Account {

    private static final double ANNUAL_INTEREST_RATE = 0.003;
    private static final double LOYAL_ANNUAL_INTEREST_RATE = 0.0045;
    private static final double MONTHLY_FEE = 6.00;
    private static final double MONTHLY_FEE_WAIVER = 300.00;

    protected boolean loyalty; //Boolean representation of holder's loyalty status.

    /**
     * Creates Saving object, invokes the superclass constructor and determines holder loyalty status.
     *
     * @param holder  -  Profile which holds the account, inherited from Account class.
     * @param closed  -  Boolean representation of account status, inherited from Account class.
     * @param balance - Initial account balance, inherited from Account class.
     * @param loyalty - Integer representation of holder's loyalty status, 1 if loyal, 0 otherwise.
     */
    public Savings(Profile holder, boolean closed, double balance, int loyalty) {
        super(holder, closed, balance);
        this.loyalty = (loyalty == 1);
    }

    /**
     * Calculates and returns the profile's monthly interest rate, implemented from the abstract
     * Account class, based on the loyalty status of the profile.
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
     * Determines and returns the profile's monthly fee based on if profile balance exceeds satisfactory
     * balance amount, implemented from the abstract Account class.
     *
     * @return Monthly fee of profile.
     */
    public double fee() {
        if (super.balance >= MONTHLY_FEE_WAIVER) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }
    }

    /**
     * Returns string representation of class's type, implemented from abstract Account class.
     *
     * @return string representation of class type.
     */
    public String getType() {
        return ("Savings");
    }


    /**
     * Takes an object and determines if it is equal to this Savings.  Savings are equal
     * if they have the same holder and type.
     *
     * @param obj - object to compare with this Savings.
     * @return - true if Savings are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoneyMarket) {
            return false;
        }

        if (obj instanceof Savings) {
            Savings s = (Savings) obj;
            return s.holder.equals(this.holder);
        }

        return false;
    }

    /**
     * Returns String representation of Savings profile, inherited from the superclass, as well
     * as an indication of the profile's loyalty status.
     *
     * @return String representation of the Savings object, with indication of loyalty.
     */
    @Override
    public String toString() {
        if (loyalty && !this.isClosed()) {
            return (super.toString() + "::Loyal");
        }

        return super.toString();
    }
}