package com.example._213project3stanleyandmatthew.accounting;

/**
 * This Checking class provides the constructors and methods for the Checking object which
 * extends from the abstract Account class and inherits its instance variables and methods.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public class Checking extends Account {

    private static final double ANNUAL_INTEREST_RATE = 0.001;
    private static final double MONTHLY_FEE = 25.00;
    private static final double FEE_WAIVER = 1000.00;

    /**
     * Creates Checking object and invokes the superclass constructor.
     *
     * @param holder  -  Profile which holds the account, inherited from Account class.
     * @param closed  -  Boolean representation of account status, inherited from Account class.
     * @param balance - Initial account balance, inherited from Account class.
     */
    public Checking(Profile holder, boolean closed, double balance) {
        super(holder, closed, balance);
    }

    /**
     * Calculates and returns the profile's monthly interest rate, implemented from the abstract
     * Account class.
     *
     * @return Monthly interest rate of profile.
     */
    public double monthlyInterest() {
        return (super.balance * ANNUAL_INTEREST_RATE) / MONTHS_IN_YEAR;
    }

    /**
     * Determines and returns the profile's monthly fee based on if profile balance exceeds satisfactory
     * balance amount, implemented from the abstract Account class.
     *
     * @return Monthly fee of profile.
     */
    public double fee() {
        if (super.balance >= FEE_WAIVER) {
            return 0;
        } else {
            return MONTHLY_FEE;
        }
    }


    /**
     * Takes an object and determines if it is equal to this Checking.  Checkings are equal
     * if they have same type and holder.
     *
     * @param obj - object to compare with this Checking.
     * @return - true if Checkings are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CollegeChecking) {
            return false;
        }

        if (obj instanceof Checking) {
            Checking c = (Checking) obj;
            return c.holder.equals(this.holder);
        }
        return false;
    }

    /**
     * Returns string representation of class's type, implemented from abstract Account class.
     *
     * @return string representation of class type.
     */
    public String getType() {
        return ("Checking");
    }
}
