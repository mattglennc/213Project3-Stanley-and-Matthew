package com.example._213project3stanleyandmatthew;

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
        outText1.appendText("Bank Teller is running. \n");
        outText2.appendText("Bank Teller is running. \n");
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
            outText1.appendText("Minimum of $2500 to open a MoneyMarket account. \n");
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
            outText1.appendText("Date of birth invalid.\n");
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
    private boolean isDoubleAndExcepOp(String initialAmount) {
        try {
            Double.parseDouble(initialAmount);
        } catch (NumberFormatException e) {
            outText1.appendText("Not a valid amount. \n");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        if (Double.parseDouble(initialAmount) <= 0) {
            outText1.appendText("Initial deposit cannot be 0 or negative. \n");
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
                outText1.appendText("Account opened. \n");
            } else {
                outText1.appendText("Account reopened. \n");
            }
        } else {
            outText1.appendText(account.holder.toString() + " same account(type) is in the database. \n");
        }
    }

    /**
     * Checks to see if there are valid inputs in the UI text boxes to run an open command with
     *
     * @return true if the inputs are valid, false if it fails any of the checks.
     */
    private boolean inputCheckerO() {
     if(fName.getText().isEmpty()){
         outText1.appendText("Missing data for opening an account.\n");
         return false;
     }
     if(lName.getText().isEmpty()){
         outText1.appendText("Missing data for opening an account.\n");
         return false;
        }
     if(dob.getValue() == null){
         outText1.appendText("Missing data for opening an account.\n");
         return false;
     }
     if(initialDeposit.getText().isEmpty()){
         outText1.appendText("Missing data for opening an account.\n");
         return false;
        }
    return true;
    }


    @FXML
    void O(ActionEvent event) {
        if(!inputCheckerO()){ //checks that enough parameters were input
            return;
        }
        String fname = fName.getText();
        String lname = lName.getText();
        String birthDate = String.valueOf(dob.getValue());
        Profile profile = new Profile(fname, lname, birthDate);
        String initialBalance = initialDeposit.getText();
        double balance = Double.parseDouble(initialBalance);
        Account account = null;
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

        else if(collegeChecking.isSelected()){ //probably make this into a private method
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
            else{
                outText1.appendText("No Campus is selected for college checking account, select a campus \n");
                return;
            }
        }
        else {
            outText1.appendText("No Account Type Selected, Select an Account Type. \n");
            return;
        }
        if (account != null) {
            validateOpening(account);
        }
        return;
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
