package com.example._213project3stanleyandmatthew;

import java.util.Calendar;

/**
 * This Date class provides the constructors and methods for the Date object.
 * The default constructor creates a date with today's date.  Alternatively,
 * a date can be input to the constructor.
 * The isValid method returns if a date is a valid calendar date.
 * The compareTo method compares two dates to order them.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */
public class Date implements Comparable<Date> {
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;
    //array holds range of days for every valid month on a non-leap year
    private static final int[] DAYS_PER_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int MAX_MONTH = 12; //maximum valid value for a month
    private static final int FEBRUARY = 2;
    private int year;
    private int month;
    private int day;

    /**
     * Takes "mm/dd/yyyy" and creates a Date object with month mm, day dd, and year yyyy.
     *
     * @param date: the date string from which the year, month, and date are taken and stored.
     */
    public Date(String date) {
        String[] dateStrings = date.split("-"); //Breaks date string into its mm, dd, and yyyy components
        for (int i = 0; i < dateStrings.length; i++) {
            if (i == 0) {
                this.year = Integer.parseInt(dateStrings[i]); // stores year, the final element of dateStrings
            } else if (i == 1) {
                this.month = Integer.parseInt(dateStrings[i]); //stores month, the first element of dateStrings
            } else {
                this.day = Integer.parseInt(dateStrings[i]); //stores day, the second element of dateStrings
            }

        }

    }

    /**
     * Creates a Date object with the current date using the Calendar class
     * and stores the year, month, and day.
     */
    public Date() {
        Calendar currentDate = Calendar.getInstance();
        this.year = currentDate.get(Calendar.YEAR);
        this.month = currentDate.get(Calendar.MONTH) + 1;
        this.day = currentDate.get(Calendar.DAY_OF_MONTH);
    } //create an object with today’s date (see Calendar class)

    /**
     * Helper method to determine if the given year is a leap year.
     * A year is only a leap year if it is divisible by 4,
     * excluding all years which not divisible by 400.
     *
     * @param year - year of the date which requires validation
     * @return true if year meets requirements for a leap year, false if it does not
     */
    private static boolean isLeapYear(int year) {
        //if year is divisible by 4 and not 100, year is a leap year
        //alternatively, if year cleanly divides by 400, year is a leap year
        return (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0);
    }

    /**
     * Returns if Date is valid.
     * A Date is only valid if the date falls within the range of days, months, and years.
     *
     * @return true if conditions are met for valid date, false if date is invalid.
     */
    public boolean isValid() {

        boolean monthIsValid = this.month > 0 && this.month <= MAX_MONTH; //check if month is in valid range
        if (!monthIsValid) {
            return false;
        }

        boolean yearIsValid = this.year >= 0; //check if year is in valid range
        if (!yearIsValid) {
            return false;
        }

        boolean dayIsValid;
        if (isLeapYear(this.year) && this.month == FEBRUARY) {
            //if year is a leap year and the month is February, add 1 to the number of days per month
            dayIsValid = this.day > 0 && this.day <= DAYS_PER_MONTH[this.month - 1] + 1;
        } else {
            //otherwise, use corresponding days per month to check if day is valid
            dayIsValid = this.day > 0 && this.day <= DAYS_PER_MONTH[this.month - 1];
        }

        return dayIsValid;
    }

    /**
     * Helper method compares two integer values to determine if one is greater than the other.
     *
     * @param number1 - first number to compare
     * @param number2 - second number to compare
     * @return 1 if number1 > number2, -1 if number1 < number2, 0 if number1 == number2
     */
    public static int compareNumbers(int number1, int number2) {
        if (number1 > number2) {
            return 1;
        } else if (number1 < number2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Compares two dates chronologically by year, month, and day.
     *
     * @param date - second Date object to compare to the first
     * @return - 1 if second Date precedes first Date chronologically,
     * -1 if first Date precedes second Date, and 0 if Dates are equal.
     */
    @Override
    public int compareTo(Date date) {
        int compareYear = compareNumbers(this.year, date.year); // compare first and second years chronologically
        if (compareYear != 0) {
            return compareYear;
        }

        int compareMonth = compareNumbers(this.month, date.month); // compare first and second months chronologically
        if (compareMonth != 0) {
            return compareMonth;
        }

        int compareDay = compareNumbers(this.day, date.day); // compare first and second days chronologically
        if (compareDay != 0) {
            return compareDay;
        }

        return 0;
    }

    /**
     * Getter method for the year instance variable
     *
     * @return - year instance variable for Date
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Helper method to return string representation of date.
     *
     * @return string representation of date in mm/dd/yyyy format.
     */
    public String toString() {

        return (this.month + "/" + this.day + "/" + this.year);
    }

}