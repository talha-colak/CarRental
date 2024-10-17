package com.talhacolak.carrental;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
/*import javafx.scene.control.Label;
import javafx.scene.control.TextField;
*/import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    /* @FXML
     private Label errorLabel;

       public void validateLogin() {
           if (loginFailed) {
               errorLabel.setText("Geçersiz Kullanıcı Adı ve Parola");
           }

       }
       */
    @FXML
    private Button goNext;

    @FXML
    protected void goNext() throws IOException {
        Stage stage = (Stage) goNext.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("dashboard.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.setTitle("Dashboard");
        stage.getScene().getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}