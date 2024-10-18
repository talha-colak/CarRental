package com.talhacolak.carrental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button button;

    @FXML
    protected void goBack() throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent scene = FXMLLoader.load(CarRentalApplication.class.getResource("login-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Login");
        //stage.setResizable(false);
        stage.getScene().getWindow().centerOnScreen();
        stage.show();
    }
}
/*@FXML
    private Label welcomeText;*/

/*@FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/