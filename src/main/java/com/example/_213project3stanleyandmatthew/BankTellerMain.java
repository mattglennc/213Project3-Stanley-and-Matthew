package com.example._213project3stanleyandmatthew;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This BankTellerMain class launches the GUI and loads its javfx file.
 * Afterwards operation and event handling is done in the Bank Teller Controller class
 *
 * @author Matthew Carrascoso & Stanley Chou
 */

public class BankTellerMain extends Application {


    /**
     * Launches the Javafx GUI
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Creates an  FXMLLoader and loads the BankTellerView.fxml file
     * afterwards the it sets the stage for the GUI
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
                Scene openClose = new Scene(fxmlLoader.load(), 600, 600);
                //Scene depositWithdraw = new Scene(fxmlLoader.load(), 500, 500);
                stage.setTitle("BankTeller");
                stage.setScene(openClose);
                stage.show();

    }
}
