package com.talhacolak.carrental.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;

public class ImageChooserController {
    @FXML
    private VBox imageChooser;

    public Button uploadButton;
    public ImageView imageView;

    @Getter
    private String imagePath;

    @FXML
    public void initialize() {
        imageChooser.getProperties().put("controller", this);
        uploadButton.setOnAction(event -> handleImageUpload());
    }

    private void handleImageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Resim Seç");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg")
        );
//        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imagePath = file.getAbsolutePath();
            System.out.println("Resim Seçildi: " + imagePath);
        } else {
            System.out.println("Dosya seçilmedi.");
        }
    }

    public void clearImage() {
        imageView.setImage(null);
        imageView = null;
        imagePath = null;
    }
}