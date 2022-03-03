package com.example._213project3stanleyandmatthew;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankTellerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 328, 240);
                stage.setTitle("BankTeller");
                stage.setScene(scene);
                stage.show();
    }
}