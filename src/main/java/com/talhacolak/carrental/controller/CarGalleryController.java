package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CarGalleryController {

    @FXML
    FontAwesomeIconView carAdd;

    private Stage carAddStage = null;

    @FXML
    private void carAdd(MouseEvent event) {
        if (carAddStage == null) {
            carAddStage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(CarRentalApplication.
                        class.getResource("car-add.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                carAddStage.setScene(scene);
                carAddStage.initStyle(StageStyle.UTILITY);
                carAddStage.setTitle("Car Adding Forms");
                carAddStage.show();
                carAddStage.setOnCloseRequest(e -> carAddStage = null);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            carAddStage.toFront();
        }
    }


}
