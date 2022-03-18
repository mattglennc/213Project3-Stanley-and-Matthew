package com.example._213project3stanleyandmatthew;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class BankTellerController {


    @FXML
    private Label accLabel;

    @FXML
    private ToggleButton accountDatabaseButton;

    @FXML
    private GridPane accountSelection;

    @FXML
    private ToggleGroup accountType;

    @FXML
    private RadioButton camden;

    @FXML
    private RadioButton checking;

    @FXML
    private Button close;

    @FXML
    private ToggleGroup college;

    @FXML
    private RadioButton collegeChecking;

    @FXML
    private ToggleButton depositWithdrawButton;

    @FXML
    private DatePicker dob;

    @FXML
    private Label dobLabel;

    @FXML
    private TextField fName;

    @FXML
    private Label fnameLabel;

    @FXML
    private ToggleGroup group;

    @FXML
    private CheckBox isLoyal;

    @FXML
    private TextField lName;

    @FXML
    private Label lnameLabel;

    @FXML
    private RadioButton moneyMarket;

    @FXML
    private RadioButton newBrunswick;

    @FXML
    private RadioButton newark;

    @FXML
    private Button open;

    @FXML
    private ToggleButton openCloseButton;

    @FXML
    private HBox openCloseButtons;

    @FXML
    private TextArea outText;

    @FXML
    private RadioButton savings;


    void clearOC(boolean openClose){
        accountSelection.setVisible(openClose);
        openCloseButtons.setVisible(openClose);
    }

    void disableSchool(boolean ccAccount){
        camden.setDisable(ccAccount);
        newBrunswick.setDisable(ccAccount);
        newark.setDisable(ccAccount);
    }

    void disableIsLoyal(boolean sAccount){
        isLoyal.setDisable(sAccount);
    }

    @FXML
    void C(ActionEvent event) {
        disableSchool(true);
        disableIsLoyal(true);
    }

    @FXML
    void CC(ActionEvent event) {
        disableSchool(false);
        disableIsLoyal(true);
    }

    @FXML
    void MM(ActionEvent event) {
        disableSchool(true);
        disableIsLoyal(true);
    }

    @FXML
    void S(ActionEvent event) {
        disableSchool(true);
        disableIsLoyal(false);
    }


    @FXML
    void accountDB(ActionEvent event) {
        clearOC(false);
    }

    @FXML
    void depositWithdraw(ActionEvent event) {
        clearOC(false);
    }

    @FXML
    void openClose(ActionEvent event) {
        clearOC(true);
    }

}
