package com.example._213project3stanleyandmatthew.accounting;

/**
 * This Profile class provides the constructors and methods for the Profile object, which
 * is what each Account is associated with.
 * It includes equals() and toString methods.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Creates Profile object, using parameters to fill necessary data fields.
     *
     * @param fname - First name of profile.
     * @param lname - Last name of profile.
     * @param dob   - Date of birth of profile.
     */
    public Profile(String fname, String lname, String dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
    }

    /**
     * Takes a profile and determines if it is equal to this Profile.  Profiles are equal
     * if they have same fname, lname, and dob.
     *
     * @param profile - Profile instance to compare with this Profile.
     * @return - true if Profiles are equal, false otherwise.
     */
    public boolean equals(Profile profile) {
        boolean fnameEqual = this.fname.toLowerCase().equals(profile.fname.toLowerCase());
        boolean lnameEqual = this.lname.toLowerCase().equals(profile.lname.toLowerCase());
        boolean dobEqual = this.dob.compareTo(profile.dob) == 0;
        return fnameEqual && lnameEqual && dobEqual;
    }

    /**
     * Check if date of birth is valid (not today or a future date)
     *
     * @return true if dob is valid (not today or a future date), otherwise false
     */
    public boolean hasValidDOB() {
        Date currentDate = new Date();
        return (this.dob.compareTo(currentDate) < 0 && this.dob.isValid());
    }

    /**
     * Getter method for the dob instance variable
     *
     * @return - the dob for this Profile
     */
    public Date getDob() {
        return this.dob;
    }


    /**
     * Returns String representation of Profile object in following format:
     * "[fname] [lname] [dob]"
     *
     * @return string representation of class Profile
     */
    @Override
    public String toString() {
        return (this.fname + " " + this.lname + " " + this.dob.toString());
    }
}