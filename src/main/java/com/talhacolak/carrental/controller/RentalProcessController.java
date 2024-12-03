package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.entity.Customer;
import com.talhacolak.carrental.entity.Inspection;
import com.talhacolak.carrental.entity.Rental;
import com.talhacolak.carrental.service.CarInspectionService;
import com.talhacolak.carrental.service.CustomerAddService;
import com.talhacolak.carrental.service.RentalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

import static com.talhacolak.carrental.service.AlertUtil.showAlert;

public class RentalProcessController {
    @FXML
    private Button deleteCustomerButton, deleteInspectionButton, refreshRentalButton, deleteRentalButton;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField licenseNumberField, firstNameField, lastNameField, phoneNumberField, emailField, madeInspectionField, registeredCustomerField, registeredCarField;

    @FXML
    private ListView customerListView, inspectionListView, carListView;

    @FXML
    private TextField kilometerField, descriptionField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private DatePicker rentalDatePicker, returnDatePicker;

    @FXML
    private CheckBox floorMatCheck, aerialCheck, firstAidKitCheck, fireExtinguisherCheck, spareTyreCheck, babySeatCheck, registrationCheck, toolSetCheck;

    @FXML
    private Slider fuelSlider;

    @FXML
    private Button registerButton, inspectionButton, finalizeButton, refreshInspectionButton, refreshCustomerButton, refreshCarButton;

    private final CustomerAddService customerAddService = new CustomerAddService();
    private final ObservableList<String> customerObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        refreshCustomerList();
    }

    @FXML
    private void registerCustomer() {

        if (licenseNumberField.getText().isEmpty() ||
                firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                phoneNumberField.getText().isEmpty() ||
                emailField.getText().isEmpty()
        ) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen tüm alanları doldurun!");
            return;
        }

        //TODO: filtrele
        Customer customer = new Customer();
        customer.setLicenseNumber(licenseNumberField.getText());
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setPhoneNumber(phoneNumberField.getText());
        customer.setEmail(emailField.getText());

        //TODO: Müşteri bilgileri kaydedilecek ve Bir sonraki sekme açılacak!!
        try {
            customerAddService.save(customer);
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müşteri Başarıyla Kaydedildi!");
            clearCustomerField();
            refreshCustomerList();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri Kaydedilemedi: " + e.getMessage());
        }
    }

    @FXML
    private void findCustomerbyLicense() {
        if (registeredCustomerField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Lisans Numaranızı Girin!");
        }

        Customer customer = customerAddService.findCustomerById(registeredCustomerField.getText());

        if (customer != null) {
            showAlert(Alert.AlertType.INFORMATION, "Müşteri Bulundu!", customer.toString());
        } else {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri Bulunamadı!");
        }

    }

    @FXML
    private void refreshCustomerList() {
        customerObservableList.clear();

        List<Customer> customers = customerAddService.getAllCustomer();
        if (customers != null && !customers.isEmpty()) {
            for (Customer customer : customers) {
                customerObservableList.add(customer.toString());
            }
        }
        customerListView.setItems(customerObservableList);

    }

    @FXML
    private void clearCustomerList() {

        customerObservableList.clear();
    }

    @FXML
    private void clearCustomerField() {
        licenseNumberField.clear();
        firstNameField.clear();
        lastNameField.clear();
        phoneNumberField.clear();
        emailField.clear();
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

        CarInspectionService carInspectionService = new CarInspectionService();
        carInspectionService.save(inspection);
    }

    @FXML
    private void refreshInspectionList() {

    }

    @FXML
    private void finalizeRental() {

        Rental rental = new Rental();
        //rental.setCar(carSelection.getValue());
        rental.setRentalDate(rentalDatePicker.getValue().atStartOfDay());
        rental.setReturnDate(returnDatePicker.getValue().atStartOfDay());
        rental.setTotalPrice(Double.parseDouble(totalPriceField.getText()));

        //TODO: Kiralama bilgileri kaydedilip işlem sonlanacak!!

        RentalService rentalService = new RentalService();
        rentalService.save(rental);

    }

    @FXML
    private void refreshCarList() {

    }
}