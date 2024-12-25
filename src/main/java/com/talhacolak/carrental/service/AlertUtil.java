package com.talhacolak.carrental.service;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static void showAlert(Alert.AlertType type, String title, String header, String context) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}