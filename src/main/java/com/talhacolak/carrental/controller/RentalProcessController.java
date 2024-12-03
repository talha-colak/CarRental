package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.entity.Customer;
import com.talhacolak.carrental.entity.Inspection;
import com.talhacolak.carrental.entity.Rental;
import com.talhacolak.carrental.service.CarInspectionService;
import com.talhacolak.carrental.service.CustomerService;
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

    private final CustomerService customerService = new CustomerService();
    private final CarInspectionService inspectionService = new CarInspectionService();

    private final ObservableList<String> customerObservableList = FXCollections.observableArrayList();
    private final ObservableList<String> inspectionObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        refreshCustomerList();
        refreshInspectionList();
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
            customerService.save(customer);
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

        Customer customer = customerService.findCustomerById(registeredCustomerField.getText());

        if (customer != null) {
            showAlert(Alert.AlertType.INFORMATION, "Müşteri Bulundu!", customer.toString());
        } else {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri Bulunamadı!");
        }

    }

    @FXML
    private void refreshCustomerList() {
        customerObservableList.clear();

        List<Customer> customers = customerService.getAllCustomer();
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

        if (kilometerField.getText().isEmpty() ||
                descriptionField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen tüm alanları doldurun!");
            return;
        }

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
        try {
            inspectionService.save(inspection);
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "İnceleme bilgileri başarıylya kaydedildi");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "İnceleme bilgileri kaydedilemedi: " + e.getMessage());
        }
    }

    @FXML
    private void refreshInspectionList() {
        inspectionObservableList.clear();

        List<Inspection> inspections = inspectionService.getAllInspections();
        if (inspections != null && !inspections.isEmpty()) {
            for (Inspection carInspection : inspections) {
                inspectionObservableList.add(carInspection.toString());
            }
        }
        inspectionListView.setItems(customerObservableList);
    }

    @FXML
    private void clearInspectionsList() {
        inspectionObservableList.clear();
    }

    @FXML
    private void clearInspectionsField() {
        kilometerField.clear();
        descriptionField.clear();
        registrationCheck.setSelected(false);
        aerialCheck.setSelected(false);
        babySeatCheck.setSelected(false);
        firstAidKitCheck.setSelected(false);
        fireExtinguisherCheck.setSelected(false);
        toolSetCheck.setSelected(false);
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