package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button goback, close, menubtn, cars;

    @FXML
    private AnchorPane sideNav1, sideNav2, dashboard_form, contentArea;

    @FXML
    private BorderPane contents;

    @FXML
    private void close() {
        Stage stage = (Stage) dashboard_form.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void carviewer() throws IOException {
        loadview("car-gallery.fxml");
    }

    @FXML
    private void goBack() throws IOException {
        loadview("dashboard.fxml");
    }

//eskidi
/*
    @FXML
    private void goBack() throws IOException {
        Stage stage = (Stage) goback.getScene().getWindow();
        stage.setScene(CarRentalApplication.loadscene("car-gallery.fxml", 1080, 720));
        stage.setTitle("Carview");
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();
    }
*/

    private void loadview(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource(fxmlFile));
            Node view = loader.load();
            contents.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
    }
}
