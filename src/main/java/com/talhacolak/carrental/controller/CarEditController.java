package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.dto.CarStatus;
import com.talhacolak.carrental.dto.Fuel;
import com.talhacolak.carrental.dto.Gear;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.service.AlertUtil;
import com.talhacolak.carrental.service.CarService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class CarEditController {
    @FXML
    private TextField brandField, modelField, yearField, priceField;

    @FXML
    private ComboBox<Fuel> fuelComboBox;

    @FXML
    private ComboBox<Gear> gearComboBox;

    @FXML
    private ComboBox<CarStatus> statusComboBox;

    @FXML
    private Button saveButton;

    private Car car;
    private Stage stage;
    private CarService carService = new CarService();

    public void setCar(Car car, Stage stage) {
        this.car = car;
        this.stage = stage;
        initializeForm();
    }

    private void initializeForm() {
        brandField.setText(car.getBrand());
        modelField.setText(car.getModel());
        yearField.setText(String.valueOf(car.getYear()));
        priceField.setText(String.valueOf(car.getPrice()));

        fuelComboBox.getItems().addAll(Fuel.values());
        fuelComboBox.setValue(car.getFuel());

        gearComboBox.getItems().addAll(Gear.values());
        gearComboBox.setValue(car.getGear());

        statusComboBox.getItems().addAll(CarStatus.values());
        statusComboBox.setValue(car.getStatus());

        saveButton.setOnAction(event -> saveCarDetails());
    }

    private void saveCarDetails() {
        try {
            if (brandField.getText().isEmpty() || modelField.getText().isEmpty() || yearField.getText().isEmpty() || priceField.getText().isEmpty()) {
                AlertUtil.showAlert(Alert.AlertType.WARNING, "Uyarı", "Boş Değer Kaydedilemez!!", "Kutucukları Boş Bırakmayınız!!");
                return;
            }

            car.setBrand(brandField.getText());
            car.setModel(modelField.getText());
            car.setYear(Integer.parseInt(yearField.getText()));
            car.setPrice(Integer.parseInt(priceField.getText()));
            car.setFuel(fuelComboBox.getValue());
            car.setGear(gearComboBox.getValue());
            if (statusComboBox.getValue() != CarStatus.AVAILABLE) {
                car.setStatus(statusComboBox.getValue());
            }

            carService.update(car);

        } catch (NumberFormatException e) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Hata", "Sayı Değeri Gerekli", "Sayı Giriniz!");
        }
    }
}
