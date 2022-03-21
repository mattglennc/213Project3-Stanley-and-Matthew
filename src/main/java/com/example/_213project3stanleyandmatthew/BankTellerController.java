package com.example._213project3stanleyandmatthew;

import com.example._213project3stanleyandmatthew.accounting.AccountDatabase;
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

    @FXML
    void O(ActionEvent event) {
        String fname = fName.getText();
        String lname = lName.getText();
        if(checking.isSelected()){

        }
        else if(savings.isSelected()){

        }
        else if(moneyMarket.isSelected()){

        }

        else if(collegeChecking.isSelected()){

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
