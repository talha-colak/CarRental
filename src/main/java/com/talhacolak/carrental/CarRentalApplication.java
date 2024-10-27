package com.talhacolak.carrental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
public class CarRentalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // INITIATE DATABASE, TABLE ETC...

        FXMLLoader fxmlLoader = new FXMLLoader(CarRentalApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}