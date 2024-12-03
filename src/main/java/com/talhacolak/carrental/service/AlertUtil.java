package com.talhacolak.carrental.service;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}