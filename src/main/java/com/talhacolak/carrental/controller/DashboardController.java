package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import com.talhacolak.carrental.entity.Car;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Insets;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button goback, close, menubtn, cars, caradd, signout;

    @FXML
    private AnchorPane sideNav1, sideNav2, dashboard_form, contentArea;

    @FXML
    private BorderPane contents;

    @FXML
    private void signOut() throws IOException {
        // Aktif Stage'i alır (get)
        Stage currentStage = (Stage) signout.getScene().getWindow();

        // login-view.fxml'i yükler
        FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("login-view.fxml"));
        Parent root = loader.load();

        // yeni Scene ataması yapar ve özellikler verir
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void addformview() throws IOException {
        //loadview("car-add.fxml");
        FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("car-add.fxml"));
        Node view = loader.load();
        BorderPane.setMargin(view, new javafx.geometry.Insets(10));
        view.prefHeight(510);
        view.prefWidth(480);
        contents.setCenter(view);
    }

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
        contents.setCenter(contentArea);
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

//    private Stage stage;
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//        stage.initStyle(StageStyle.TRANSPARENT);
//    }
}
