package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.service.CarService;
import com.talhacolak.carrental.dto.CarStatus;
import com.talhacolak.carrental.dto.Category;
import com.talhacolak.carrental.dto.Fuel;
import com.talhacolak.carrental.dto.Gear;
import com.talhacolak.carrental.entity.Car;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

import static com.talhacolak.carrental.service.AlertUtil.showAlert;

public class CarAddController {

    private final CarService carService = new CarService();

    @FXML
    private VBox imageChooser;
    private ImageChooserController imageChooserController;
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
        imageChooserController = (ImageChooserController) imageChooser.getProperties().get("controller");

        bodyDropdown.setItems(FXCollections.observableArrayList(Category.values()));
        fuelDropdown.setItems(FXCollections.observableArrayList(Fuel.values()));
        gearDropdown.setItems(FXCollections.observableArrayList(Gear.values()));
        statusDropdown.setItems(FXCollections.observableArrayList(CarStatus.values()));
        plateField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d{0,2}[A-Z]{0,3}\\d{0,3}") ? change : null));

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
        String imagePath = imageChooserController.getImagePath();

        if (brand.isEmpty() || model.isEmpty() || plate.isEmpty() || year.isEmpty() || price.isEmpty()) {
            //showAlert("Hata!", "Tüm kutucuklar doldurulmalı ve Resim seçilmeli!");
            showAlert(Alert.AlertType.INFORMATION, "Hata!", "Tüm kutucuklar doldurulmalı ve Resim seçilmeli!");
            return;
        }
        if (imagePath == null || imagePath.isEmpty()) {
            imagePath = "C:\\Users\\Talha Çolak\\IdeaProjects\\CarRentalSystem\\src\\main\\resources\\com\\talhacolak\\carrental\\images\\placeholder.jpg";
        }
        if (!plate.matches("\\d{0,2}[A-Z]{0,3}\\d{0,3}")) {
            //showAlert("Hata!", "Plaka '00ABC000' formatında girilmeli!");
            showAlert(Alert.AlertType.INFORMATION, "Hata!", "Plaka '00ABC000' formatında girilmeli!");
            return;
        }
        if (!year.matches("\\d{4}") || Integer.parseInt(year) < 1900 || Integer.parseInt(year) > LocalDate.now().getYear()) {
            //showAlert("Hata!", "Yıl değeri bugünden büyük olamaz!");
            showAlert(Alert.AlertType.INFORMATION, "Hata!", "Yıl değeri bugünden büyük olamaz!");
            return;
        }
        if (!price.matches("\\d*(\\.\\d{0,2})?")) {
            showAlert(Alert.AlertType.INFORMATION, "Hata!", "Günlük fiyatı doğru giriniz!");
            return;
        }

        int yearText = Integer.parseInt(year);
        double priceText = Double.parseDouble(price);
        Car car = new Car();
        car.setLicensePlate(plate);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(yearText);
        car.setPrice((int) priceText);
        car.setImageUrl("file:///" + imagePath);
        car.setCategory(bodyDropdown.getValue());
        car.setFuel(fuelDropdown.getValue());
        car.setGear(gearDropdown.getValue());
        car.setStatus(statusDropdown.getValue());

        try {
            saveCar(car);
            //showAlert("Başarlı!", "Bilgiler başarıyla eklendi!");
            showAlert(Alert.AlertType.INFORMATION, "Başarlı!", "Bilgiler başarıyla eklendi!");
            clearFields();
        } catch (Exception e) {
            showAlert(Alert.AlertType.INFORMATION, "Hata!", "Araba kaydedilirken bir sorun oluştu!");
            e.printStackTrace();
        }
    }

    private void saveCar(Car car) {
        carService.save(car);
    }

/*
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
*/

    private void clearFields() {
        plateField.clear();
        brandField.clear();
        modelField.clear();
        yearField.clear();
        priceField.clear();
        imageChooserController.clearImage();
        bodyDropdown.getSelectionModel().select(Category.UNDEFINED);
        fuelDropdown.getSelectionModel().select(Fuel.UNDEFINED);
        gearDropdown.getSelectionModel().select(Gear.UNDEFINED);
        statusDropdown.getSelectionModel().select(CarStatus.UNDEFINED);
    }

}
