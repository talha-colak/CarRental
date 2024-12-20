package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import com.talhacolak.carrental.dto.Role;
import com.talhacolak.carrental.entity.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;

public class DashboardController {

    @FXML
    private Button goHome, close, carrent, cars, caradd, signout, useradd;

    @FXML
    private AnchorPane dashboard_form, contentArea;

    @FXML
    private BorderPane contents;

    @FXML
    private FontAwesomeIconView userAdd;

    @FXML
    public void initialize() {
        User currentUser = LoginController.getLoggedInUser();
        System.out.println(currentUser);
        if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
            useradd.setDisable(true);
            useradd.setVisible(false);
        } else {
            useradd.setDisable(false);
            useradd.setVisible(true);
        }

    }

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
    private void close() {
//        Stage stage = (Stage) dashboard_form.getScene().getWindow();
        Stage stage = (Stage) contents.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addformview() throws IOException {
        loadview("car-add.fxml");

        /*FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("car-add.fxml"));
        Node view = loader.load();
        BorderPane.setMargin(view, new javafx.geometry.Insets(10));
        view.prefHeight(510);
        view.prefWidth(480);
        contents.setCenter(view);*/
    }

//    @FXML
//    private void helloWorld() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setHeaderText("Test");
//        alert.setTitle("Demo");
//        alert.setContentText("HelloWorld");
//        alert.showAndWait();
//    }

    @FXML
    private void carviewer() throws IOException {
        loadview("car-gallery.fxml");
    }

    @FXML
    private void carrent() {
        loadview("car-rental.fxml");
    }

    @FXML
    private void userAdd() {
        loadview("user-add.fxml");
    }

    @FXML
    private void goHome() throws IOException {
        loadview("car-gallery.fxml");
//        contents.setCenter(contentArea);
    }

    private void loadview(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource(fxmlFile));
            Node view = loader.load();
            contents.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}