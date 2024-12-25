package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.entity.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CarDetailsController {
    @FXML
    private VBox carDetailsVBox;

    @FXML
    private Label brandLabel, modelLabel, priceLabel, fuelLabel,
            gearLabel, statusLabel, yearLabel, plateLabel;
    @FXML
    private ImageView carImageView;

    private Car car;

    public void setCar(Car car) {
        this.car = car;
        displayCarDetails();
    }

    private void displayCarDetails() {
        plateLabel.setText(car.getLicensePlate());
        brandLabel.setText(car.getBrand());
        modelLabel.setText(car.getModel());
        priceLabel.setText(String.valueOf(car.getPrice()) + " TL/Günlük");
        fuelLabel.setText(car.getFuel().name());
//        fuelLabel.setText(car.getFuel().toString());
        gearLabel.setText(car.getGear().name());
        statusLabel.setText(car.getStatus().name());
        yearLabel.setText(String.valueOf(car.getYear()));

        String imageUrl = car.getImageUrl() != null ? car.getImageUrl() :
                "file:///C:/Users/Talha Çolak/IdeaProjects/CarRentalSystem/src/main/resources/com/talhacolak/carrental/images/placeholder.jpg";
        Image carImage = new Image(imageUrl);
        carImageView.setImage(carImage);

    }
}
