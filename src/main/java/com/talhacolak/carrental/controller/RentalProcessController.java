package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.entity.Customer;
import com.talhacolak.carrental.entity.Inspection;
import com.talhacolak.carrental.entity.Rental;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RentalProcessController {

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField licenseNumberField, firstNameField, lastNameField, phoneNumberField, emailField;

    @FXML
    private TextField kilometerField, descriptionField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private DatePicker rentalDatePicker, returnDatePicker;

    @FXML
    private ComboBox<Car> carSelection;

    @FXML
    private CheckBox floorMatCheck, aerialCheck, firstAidKitCheck, fireExtinguisherCheck, spareTyreCheck, babySeatCheck, registrationCheck, toolSetCheck;

    @FXML
    private Slider fuelSlider;

    @FXML
    private Button registerButton, inspectionButton;

    @FXML
    private void registerCustomer() {

        Customer customer = new Customer();
        customer.setLicenseNumber(licenseNumberField.getText());
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
        customer.setEmail(emailField.getText());

        //TODO: Müşteri bilgileri kaydedilecek ve Bir sonraki sekme açılacak!!

    }

    @FXML
    private void saveInspection() {

        Inspection inspection = new Inspection();
        inspection.setKilometer(Integer.parseInt(kilometerField.getText()));
        inspection.setFuelStatus((int) fuelSlider.getValue());
        inspection.setFloorMat(floorMatCheck.isSelected());
        inspection.setAerial(aerialCheck.isSelected());
        inspection.setFirstAidKit(firstAidKitCheck.isSelected());
        inspection.setFireExtinguisher(fireExtinguisherCheck.isSelected());
        inspection.setSpareTyre(spareTyreCheck.isSelected());
        inspection.setBabySeat(babySeatCheck.isSelected());
        inspection.setRegistration(registrationCheck.isSelected());
        inspection.setToolSet(toolSetCheck.isSelected());
        inspection.setDescription(descriptionField.getText());

        //TODO: Araç inceleme formundaki bilgiler kaydedilecek ve Bir sonraki sekme açılacak!!

    }

    @FXML
    private void finalizeRental() {

        Rental rental = new Rental();
        rental.setCar(carSelection.getValue());
        rental.setRentalDate(rentalDatePicker.getValue().atStartOfDay());
        rental.setReturnDate(returnDatePicker.getValue().atStartOfDay());
        rental.setTotalPrice(Double.parseDouble(totalPriceField.getText()));

        //TODO: Kiralama bilgileri kaydedilip işlem sonlanacak!!
    }
}