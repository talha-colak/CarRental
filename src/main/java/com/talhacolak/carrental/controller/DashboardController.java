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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController {

    @FXML
    private ComboBox languageSelector;

    @FXML
    private Button goHome, close, carrent, cars, caradd, signout, useradd, carretrieve;

    @FXML
    private AnchorPane dashboard_form, contentArea;

    @FXML
    private BorderPane contents;

    @FXML
    private FontAwesomeIconView userAdd;

    private ResourceBundle bundle;
    private Locale currentLocale;

    @FXML
    public void initialize() throws IOException {

        User currentUser = LoginController.getLoggedInUser();
        System.out.println(currentUser);
        if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
            useradd.setDisable(false);
            useradd.setVisible(true);
        } else if (currentUser != null && currentUser.getRole() == Role.USER) {
            useradd.setDisable(true);
            useradd.setVisible(false);
        } else {
            signOut();
        }

        loadview("car-gallery.fxml");
    }

    @FXML
    private void signOut() throws IOException {
        // Aktif Stage'i alır (get)
        Stage currentStage = (Stage) signout.getScene().getWindow();

        Locale defaultLocale = new Locale("tr", "TR");
        Locale.setDefault(defaultLocale);

        // login-view.fxml'i yükler
        FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("login-view.fxml"));

        ResourceBundle bundle = ResourceBundle.getBundle("languages.crs_localization", defaultLocale);
        loader.setResources(bundle);

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
    private void goHome() throws IOException {
        loadview("car-gallery.fxml");
    }

    @FXML
    private void carviewer() throws IOException {
        loadview("car-gallery.fxml");
    }

    @FXML
    private void addformview() throws IOException {
        loadview("car-add.fxml");
    }

    @FXML
    private void carrent() {
        loadview("car-rental.fxml");
    }

    @FXML
    private void retrievecar() {
        loadview("rental-retrieve.fxml");
    }

    @FXML
    private void userAdd() {
        loadview("user-add.fxml");
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