package com.example._213project3stanleyandmatthew;

import com.example._213project3stanleyandmatthew.accounting.AccountDatabase;
import com.example._213project3stanleyandmatthew.accounting.Profile;
import com.example._213project3stanleyandmatthew.accounting.Checking;
import com.example._213project3stanleyandmatthew.accounting.CollegeChecking;
import com.example._213project3stanleyandmatthew.accounting.Date;
import com.example._213project3stanleyandmatthew.accounting.MoneyMarket;
import com.example._213project3stanleyandmatthew.accounting.Savings;
import com.example._213project3stanleyandmatthew.accounting.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankTellerController {

    private AccountDatabase accountDatabase;

    private static final boolean OPEN = false;
    private static final int DEFAULTSCHOOL = 1;
    private static final int DEFAULTLOYALTY = 1;
    private static final int LOYAL = 1;
    private static final int NOTLOYAL = 0;
    private static final int CAMDEN = 2;
    private static final int NEWARK = 1;
    private static final int NEWBRUNSWICK = 0;
    private static final int DEFAULTBALANCE = 1;
    private static final int MAX_VALID_SCHOOL = 2;
    private static final int MIN_MM_DEPOSIT = 2500;
    private static final int MIN_VALID_TOKENS = 4;

    @FXML
    private ToggleGroup accountTypeOC;

    @FXML
    private RadioButton camden;

    @FXML
    private RadioButton checking;

    @FXML
    private RadioButton collegeChecking;

    @FXML
    private ToggleGroup collegeOC;

    @FXML
    private VBox collegeOCbox;

    @FXML
    private Group collegeOCgroup;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField fName;

    @FXML
    private TextField initialDeposit;

    @FXML
    private CheckBox isLoyalOC;

    @FXML
    private TextField lName;

    @FXML
    private RadioButton moneyMarket;

    @FXML
    private RadioButton newBrunswick;

    @FXML
    private RadioButton newark;

    @FXML
    private TextArea outText1;
    @FXML
    private TextArea outText2;

    @FXML
    private RadioButton savings;

    @FXML
    void initialize(){
        this.accountDatabase = new AccountDatabase();
        String l = "Gay";
        outText1.appendText(l);
        outText2.appendText(l);
    }

    void disableCC(boolean cc){
        collegeOCgroup.setDisable(cc);
        camden.setSelected(false);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
    }

    void disableLoyalCustomer(boolean s){
        isLoyalOC.setDisable(s);
        isLoyalOC.setSelected(false);
    }

    @FXML
    void C(ActionEvent event) {
        disableCC(true);
        disableLoyalCustomer(true);
    }

    @FXML
    void CC(ActionEvent event) {
        disableCC(false);
        disableLoyalCustomer(true);
    }

    @FXML
    void D(ActionEvent event) {

    }

    @FXML
    void MM(ActionEvent event) {
        disableCC(true);
        disableLoyalCustomer(true);
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

    @FXML
    void O(ActionEvent event) {
        String fname = fName.getText();
        String lname = lName.getText();
        String birthDate = String.valueOf(dob.getValue());
        Profile profile = new Profile(fname, lname, birthDate);
        String initialBalance = initialDeposit.getText();
        double balance = Double.parseDouble(initialBalance);
        Account account;
        if (!isDoubleAndExcepOp(initialBalance) || !profileIsValid(profile)) {
            return;
        }
        if(checking.isSelected()){
            account = new Checking(profile, OPEN, balance);
        }
        else if(savings.isSelected()){
            if(isLoyalOC.isSelected()){
                account = new Savings(profile, OPEN, balance, LOYAL);
            }
            else{
                account = new Savings(profile, OPEN, balance, NOTLOYAL);
            }
        }
        else if(moneyMarket.isSelected()){
            account = mmIsValid(balance) ? new MoneyMarket(profile, OPEN, balance) : null;
        }

        else if(collegeChecking.isSelected()){
            if(camden.isSelected()){
                int school = CAMDEN;
                account = hasValidSchool(school) ? new CollegeChecking(profile, OPEN, balance, school) : null;
            }
            else if(newark.isSelected()){
                int school = NEWARK;
                account = hasValidSchool(school) ? new CollegeChecking(profile, OPEN, balance, school) : null;
            }
            else if(newBrunswick.isSelected()){
                int school = NEWBRUNSWICK;
                account = hasValidSchool(school) ? new CollegeChecking(profile, OPEN, balance, school) : null;
            }
        }
        if (account != null) {
            validateOpening(account);
        }
    }

    @FXML
    void P(ActionEvent event) {

    }

    @FXML
    void PI(ActionEvent event) {

    }

    @FXML
    void PT(ActionEvent event) {

    }

    @FXML
    void S(ActionEvent event) {
        disableCC(true);
        disableLoyalCustomer(false);
    }

    @FXML
    void U(ActionEvent event) {

    }

    @FXML
    void W(ActionEvent event) {

    }

}
