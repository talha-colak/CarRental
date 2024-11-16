package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Button goback;

    @FXML
    private Button cars;

    @FXML
    private void carview() throws IOException{
    }

    @FXML
    private void goBack() throws IOException {
    Stage stage = (Stage) goback.getScene().getWindow();
    stage.setScene(CarRentalApplication.loadscene("demo-carview.fxml",1080,720));
    stage.setTitle("Carview");
    stage.setMaximized(true);
    stage.centerOnScreen();
    stage.setResizable(true);
    stage.show();
    }
}
/*@FXML
    private Label welcomeText;*/

/*@FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/