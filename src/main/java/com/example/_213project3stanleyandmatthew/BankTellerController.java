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


/**
 * This BankTellerController class holds an AccountDatabase and acts as a way to interact with it using the JavaFX GUI.
 * the necessary commands all have their own even handlers that can be called by clicking on their respective buttons
 * in the GUI. Private helper methods are included
 * initialize() creates the AccountDatabase and starts its operation.
 *
 * @author Matthew Carrascoso & Stanley Chou
 */

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

    /**
     * Takes a String depositAmount from the D command and checks if the depositAmount is valid
     *
     * @param depositAmount: the string with the supposed depositAmount for the deposit call
     * @return true if the depositAmount is valid, false if it fails any of the checks.
     */
    private boolean isDoubleAndExcepDep(String depositAmount) {
        try {
            Double.parseDouble(depositAmount);
        } catch (NumberFormatException e) {
            outText1.appendText("Not a valid amount.\n");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        if (Double.parseDouble(depositAmount) <= 0) {
            outText1.appendText("Deposit - amount cannot be 0 or negative.\n");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Checks to see if there are valid inputs in the UI text boxes to run a deposit command with
     *
     * @return true if the inputs are valid, false if it fails any of the checks.
     */
    private boolean inputCheckerD() {
        if(fName.getText().isEmpty()){
            outText1.appendText("Missing data for depositing to an account.\n");
            return false;
        }
        if(lName.getText().isEmpty()){
            outText1.appendText("Missing data for depositing to an account.\n");
            return false;
        }
        if(dob.getValue() == null){
            outText1.appendText("Missing data for depositing to an account.\n");
            return false;
        }
        if(initialDeposit.getText().isEmpty()){
            outText1.appendText("Missing data for depositing to an account.\n");
            return false;
        }
        if (!isDoubleAndExcepOp(initialDeposit.getText())) {
            return false;
        }
        return true;
    }


    /**
     * Takes the parameters needed to deposit inta a specific account
     * the parameters are loaded form the GUI and then checked  in inputCheckerD() to ensure
     * they are valid parameters, once all checks are passed, the deposit method
     * from the AccountDatabase class is called to deposit the specified amount into the account
     */
    @FXML
    void D(ActionEvent event) {
        if(!inputCheckerD()){ //checks that enough parameters were input
            return;
        }
        String fname = fName.getText();
        String lname = lName.getText();
        String birthDate = String.valueOf(dob.getValue());
        Profile profile = new Profile(fname, lname, birthDate);
        String depositAmount = initialDeposit.getText();
        Account account = null;
        if (checking.isSelected()) {
            account = new Checking(profile, OPEN, Double.parseDouble(depositAmount));
        } else if (collegeChecking.isSelected()) {
            account = new CollegeChecking(profile, OPEN, Double.parseDouble(depositAmount), DEFAULTSCHOOL);
        } else if (moneyMarket.isSelected()) {
            account = new MoneyMarket(profile, OPEN, Double.parseDouble(depositAmount));
        } else if (savings.isSelected()){ //must equal S if it reaches here
            account = new Savings(profile, OPEN, Double.parseDouble(depositAmount), DEFAULTLOYALTY);
        }
        else{
            outText1.appendText("Select an Account Type to deposit into. \n");
            return;
        }
        boolean deposit = this.accountDatabase.isValidDeposit(account);
        if (deposit) {
            outText1.appendText("Deposit - balance updated. \n");
        } else {
            outText1.appendText(profile.toString() + " " + account.getType() + " is not in the database. \n");
        }
        return;
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
            System.out.println("Invalid campus code.\n");
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
     if (!isDoubleAndExcepOp(initialDeposit.getText())) {
         return false;
        }
    return true;
    }

    /**
     * Takes the parameters needed to open a new account
     * the parameters are loaded form the GUI and then checked in inputCheckerO() and
     * isDoubleAndExcepOp() to ensure they are valid parameters to open an account with,
     * once all checks are passed the open method from the AccountDatabase class is called to open
     * an account with the specified parameters in the AccountDatabase.
     */
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
        if (!profileIsValid(profile)) {
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

    /**
     * Checks to see if there are valid inputs in the UI text boxes to run a close command with
     *
     * @return true if the inputs are valid, false if it fails any of the checks.
     */
    private boolean inputCheckerC() {
        if(fName.getText().isEmpty()){
            outText1.appendText("Missing data for closing an account.\n");
            return false;
        }
        if(lName.getText().isEmpty()){
            outText1.appendText("Missing data for closing an account.\n");
            return false;
        }
        if(dob.getValue() == null){
            outText1.appendText("Missing data for closing an account.\n");
            return false;
        }
        return true;
    }


    /**
     * Takes the parameters needed to close a specific account
     * the parameters are loaded from the GUI and then checked to ensure
     * they are valid parameters. Then the close() method
     * from the AccountDatabase class is called to close the specified account
     */
    @FXML
    void Close(ActionEvent event) {
        if(!inputCheckerC()){ //checks that enough parameters were input
            return;
        }
        String fname = fName.getText();
        String lname = lName.getText();
        String birthDate = String.valueOf(dob.getValue());
        Profile profile = new Profile(fname, lname, birthDate);
        Account account;
        if (checking.isSelected()) {
            account = new Checking(profile, OPEN, DEFAULTBALANCE);
        } else if (collegeChecking.isSelected()) {
            account = new CollegeChecking(profile, OPEN, DEFAULTBALANCE, DEFAULTSCHOOL);
        } else if (moneyMarket.isSelected()) {
            account = new MoneyMarket(profile, OPEN, DEFAULTBALANCE);
        } else if (savings.isSelected()) {
            account = new Savings(profile, OPEN, DEFAULTBALANCE, DEFAULTLOYALTY);
        } else{ //reaches here only if no account type was checcked
            outText1.appendText("Select an Account Type to close. \n");
            return;
        }
        boolean accountClosed = accountDatabase.close(account);
        if (accountClosed) {
            outText1.appendText("Account closed. \n");
        } else if (accountDatabase.accountExists(account)) {
            outText1.appendText("Account is closed already. \n");
        } else {
            outText1.appendText(profile.toString() + " " + account.getType() + " is not in the database. \n");
        }

        return;
    }

    /**
     * Prints all the accounts in the AccountDatabase in their current order
     * by calling the print method in the AccountDatabase class.
     */
    @FXML
    void P(ActionEvent event) {
        if (this.accountDatabase.getNumAccounts() == 0) {
            outText2.appendText("Account Database is empty!\n");
            return;
        }
        else{
            outText2.appendText("\n");
            outText2.appendText("*list of accounts in the database*\n");
            String accounts = this.accountDatabase.print();
            outText2.appendText(accounts);
            outText2.appendText("*end of list*\n");
            outText2.appendText("\n");
            return;
            }
    }

    /**
     * Prints all the accounts in the AccountDatabase sorted by account type
     * by calling the printFeeAndInterest method in the AccountDatabase class.
     */
    @FXML
    void PI(ActionEvent event) {
        if (this.accountDatabase.getNumAccounts() == 0) {
            outText2.appendText("Account Database is empty!\n");
            return;
        }
        else{
            outText2.appendText("\n");
            outText2.appendText("*list of accounts with fee and monthly interest\n");
            String accounts = this.accountDatabase.printFeeAndInterest();
            outText2.appendText(accounts);
            outText2.appendText("*end of list.\n");
            outText2.appendText("\n");
            return;
        }
    }

    /**
     * Prints all the accounts in the AccountDatabase sorted by account type
     * by calling the printByAccountType method in the AccountDatabase class.
     */
    @FXML
    void PT(ActionEvent event) {
        if (this.accountDatabase.getNumAccounts() == 0) {
            outText2.appendText("Account Database is empty!\n");
            return;
        }
        else{
            outText2.appendText("\n");
            outText2.appendText("*list of accounts by account type.\n");
            String accounts = this.accountDatabase.printByAccountType();
            outText2.appendText(accounts);
            outText2.appendText("*end of list.\n");
            outText2.appendText("\n");
            return;
        }
    }

    @FXML
    void S(ActionEvent event) {
        disableCC(true);
        disableLoyalCustomer(false);
    }

    /**
     * Updates the balances of all the accounts in the AccountDatabase with their monthly interest rate and fee
     * by calling the updateBalances method in the AccountDatabase class.
     */
    @FXML
    void UB(ActionEvent event) {
        if (this.accountDatabase.getNumAccounts() == 0) {
            outText2.appendText("Account Database is empty!\n");
            return;
        }
        else{
            outText2.appendText("\n");
            outText2.appendText("*list of accounts with updated balance\n");
            String accounts = this.accountDatabase.updateBalances();
            outText2.appendText(accounts);
            outText2.appendText("*end of list.\n");
            outText2.appendText("\n");
            return;
        }
    }

    /**
     * Checks to see if there are valid inputs in the UI text boxes to run a withdraw command with
     *
     * @return true if the inputs are valid, false if it fails any of the checks.
     */
    private boolean inputCheckerW() {
        if(fName.getText().isEmpty()){
            outText1.appendText("Missing data for withdrawing from an account.\n");
            return false;
        }
        if(lName.getText().isEmpty()){
            outText1.appendText("Missing data for withdrawing from an account.\n");
            return false;
        }
        if(dob.getValue() == null){
            outText1.appendText("Missing data for withdrawing from an account.\n");
            return false;
        }
        if(initialDeposit.getText().isEmpty()){
            outText1.appendText("Missing data for withdrawing from an account.\n");
            return false;
        }
        if (!isDoubleAndExcepOp(initialDeposit.getText())) {
            return false;
        }
        return true;
    }

    /**
     * Takes a String withdrawal amount from the W command and checks if the withdrawAmount is valid
     *
     * @param withdrawAmount: the string with the supposed withdrawAmount for the withdraw call
     * @return true if the withdrawAmount is valid, false if it fails any of the checks.
     */
    private boolean isDoubleAndExcepWith(String withdrawAmount) {
        try {
            Double.parseDouble(withdrawAmount);
        } catch (NumberFormatException e) {
            outText1.appendText("Not a valid amount. \n");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        if (Double.parseDouble(withdrawAmount) <= 0) {
            outText1.appendText("Withdraw - amount cannot be 0 or negative. \n");
            return false;
        }
        // only got here if we didn't return false
        return true;
    }



    /**
     * Takes the parameters needed to withdraw from a specific account
     * the parameters are loaded form the GUI and then checked  in inputCheckerW() to ensure
     * they are valid parameters, once all checks are passed the withdraw method
     * from the AccountDatabase class is called to withdraw the specified amount from the account
     */
    @FXML
    void W(ActionEvent event) {
        if(!inputCheckerW()){ //checks that enough parameters were input
            return;
        }
        String fname = fName.getText();
        String lname = lName.getText();
        String birthDate = String.valueOf(dob.getValue());
        Profile profile = new Profile(fname, lname, birthDate);
        Account account = null;
        String withdrawAmount = initialDeposit.getText();
        if (checking.isSelected()) {
            account = new Checking(profile, OPEN, Double.parseDouble(withdrawAmount));
        } else if (collegeChecking.isSelected()) {
            account = new CollegeChecking(profile, OPEN, Double.parseDouble(withdrawAmount), DEFAULTSCHOOL);
        } else if (moneyMarket.isSelected()) {
            account = new MoneyMarket(profile, OPEN, Double.parseDouble(withdrawAmount));
        } else if (savings.isSelected()){ //must equal S if it reaches here
            account = new Savings(profile, OPEN, Double.parseDouble(withdrawAmount), DEFAULTLOYALTY);
        } else{
            outText1.appendText("Select an Account Type to withdraw from. \n");
            return;
        }
        boolean successfulWithdraw = this.accountDatabase.withdraw(account);
        if (successfulWithdraw) {
            outText1.appendText("Withdraw - balance updated. \n");
        } else if (accountDatabase.accountExists(account)) {
            outText1.appendText("Withdraw - insufficient fund. \n");
        } else {
            outText1.appendText(profile.toString() + " " + account.getType() + " is not in the database. \n");
        }
        return;
    }

}
