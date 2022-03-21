package com.example._213project3stanleyandmatthew;

/**
 * This CollegeChecking class provides the constructors and methods for the CollegeChecking object which
 * extends from the abstract Account class and inherits its instance variables and methods.
 * The class includes the school instance variable, which is a representation of the school the
 * account is associated with.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public class CollegeChecking extends Checking {

    private static final double ANNUAL_INTEREST_RATE = 0.0025;

    private String school; // Holds name of school associated with this account.

    /**
     * Creates CollegeChecking object, invokes the superclass constructor and matches
     * school number with a corresponding school name.
     *
     * @param holder       -  Profile which holds the account, inherited from Account class.
     * @param closed       -  Boolean representation of account status, inherited from Account class.
     * @param balance      - Initial account balance, inherited from Account class.
     * @param schoolNumber - Integer which corresponds to the school to which the account belongs.
     */
    public CollegeChecking(Profile holder, boolean closed, double balance, int schoolNumber) {
        super(holder, closed, balance);
        switch (schoolNumber) {
            case 0:
                this.school = "NEW_BRUNSWICK";
                break;
            case 1:
                this.school = "NEWARK";
                break;
            default:
                this.school = "CAMDEN";
                break;
        }
    }

    /**
     * Setter method for the CollegeChecking school name.
     *
     * @param school: New school name.
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Getter method for the CollegeChecking school name.
     *
     * @return the name of the school of the CollegeChecking.
     */
    public String getSchool() {
        return this.school;
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
     * Returns the profile's monthly fee, implemented from the abstract
     * Account class.  Because it is being implemented by CollegeChecking class, fee is 0.
     *
     * @return Monthly fee of profile.
     */
    public double fee() {
        return 0;
    }

    /**
     * Returns string representation of class's type, implemented from abstract Account class.
     *
     * @return string representation of class type.
     */
    public String getType() {
        return ("College Checking");
    }

    /**
     * Takes an object and determines if it is equal to this CollegeChecking.  CollegeCheckings are equal
     * if they have same holder and type (this includes Checking accounts).
     *
     * @param obj - object to compare with this CollegeChecking.
     * @return - true if accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CollegeChecking) {
            CollegeChecking cc = (CollegeChecking) obj;
            return cc.holder.equals(this.holder);
        }
        return false;
    }


    /**
     * Returns String representation of CollegeChecking profile, inherited from the superclass, as well
     * as the school of the profile.
     *
     * @return String representation of the CollegeChecking object, including the school.
     */
    @Override
    public String toString() {
        return (super.toString() + "::" + this.school);
    }
}