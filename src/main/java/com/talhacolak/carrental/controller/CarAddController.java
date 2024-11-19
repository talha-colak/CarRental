package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.service.CarAddService;
import com.talhacolak.carrental.dto.CarStatus;
import com.talhacolak.carrental.dto.Category;
import com.talhacolak.carrental.dto.Fuel;
import com.talhacolak.carrental.dto.Gear;
import com.talhacolak.carrental.entity.Car;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.LocalDate;

public class CarAddController {

    private final CarAddService carAddService = new CarAddService();

    @FXML
    private TextField plateField, brandField, modelField, yearField, priceField;    // 22 ABC 123

    @FXML
    private ComboBox<Category> bodyDropdown;
    @FXML
    private ComboBox<Fuel> fuelDropdown;
    @FXML
    private ComboBox<Gear> gearDropdown;
    @FXML
    private ComboBox<CarStatus> statusDropdown;

    @FXML
    public void initialize() {
        bodyDropdown.setItems(FXCollections.observableArrayList(Category.values()));
        fuelDropdown.setItems(FXCollections.observableArrayList(Fuel.values()));
        gearDropdown.setItems(FXCollections.observableArrayList(Gear.values()));
        statusDropdown.setItems(FXCollections.observableArrayList(CarStatus.values()));

        plateField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("[A-Z0-9]{0,8}") ? change : null));

        yearField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d{0,4}") ? change : null));

        priceField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*(\\.\\d{0,2})?") ? change : null));

    }

    @FXML
    private void addCar() {

        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String plate = plateField.getText().trim();
        String year = yearField.getText().trim();
        String price = priceField.getText().trim();

        if (brand.isEmpty() || model.isEmpty() || plate.isEmpty() || year.isEmpty() || price.isEmpty()) {
            showAlert("Hata!", "Tüm kutucuklar doldurulmalı!");
            return;
        }
        if (!plate.matches("[A-Z0-9]{8}")) {
            showAlert("Hata!", "Plaka uygun '00ABC000' formatında girilmeli!");
            return;
        }
        if (!year.matches("\\d{4}") || Integer.parseInt(year) < 1900 || Integer.parseInt(year) > LocalDate.now().getYear()) {
            showAlert("Hata!", "Yıl değeri bugünden büyük olamaz!");
            return;
        }
        if (!price.matches("\\d*(\\.\\d{0,2})?")) {
            showAlert("Hata!", "Günlük fiyatı doğru giriniz!");
            return;
        }

        int yearText = Integer.parseInt(year);
        double priceText = Double.parseDouble(price);
        // Car car = new Car(brand, model, plate, yearText, priceText, fuelDropdown.getValue(), gearDropdown.getValue(), bodyDropdown.getValue());

        Car car = new Car();
        car.setLicensePlate(plate);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(yearText);
        car.setPrice((int) priceText);
        car.setCategory(bodyDropdown.getValue());
        car.setFuel(fuelDropdown.getValue());
        car.setGear(gearDropdown.getValue());
        car.setStatus(statusDropdown.getValue());
        //saveCar(car);
        //showAlert("Başarılı!", "Bilgiler başarıyla eklendi");

        try {
            saveCar(car);
            showAlert("Başarlı!", "Bilgiler başarıyla eklendi!");
            clearFields();
        } catch (Exception e) {
            showAlert("Hata!", "Araba kaydedilirken bir sorun oluştu!");
            e.printStackTrace();
        }
    }

    private void saveCar(Car car) {
        carAddService.saveCar(car);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        plateField.clear();
        brandField.clear();
        modelField.clear();
        yearField.clear();
        priceField.clear();
        bodyDropdown.getSelectionModel().clearSelection();
        fuelDropdown.getSelectionModel().clearSelection();
        gearDropdown.getSelectionModel().clearSelection();
        statusDropdown.getSelectionModel().clearSelection();
    }

}
