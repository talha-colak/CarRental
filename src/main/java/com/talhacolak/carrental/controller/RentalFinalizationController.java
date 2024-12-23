package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.entity.Rental;
import com.talhacolak.carrental.service.RentalService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;

import static com.talhacolak.carrental.service.AlertUtil.showAlert;

public class RentalFinalizationController {

    private final RentalService rentalService = new RentalService();

    @FXML
    private CheckBox aerialCheck;

    @FXML
    private CheckBox babySeatCheck;

    @FXML
    private Tab carSelectionTab;

    @FXML
    private Tab customerRegistrationTab;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField emailField;

    @FXML
    private Button finalizeButton;

    @FXML
    private CheckBox fireExtinguisherCheck;

    @FXML
    private CheckBox firstAidKitCheck;

    @FXML
    private TextField firstNameField;

    @FXML
    private CheckBox floorMatCheck;

    @FXML
    private Slider fuelSlider;

    @FXML
    private Button inspectionButton;

    @FXML
    private Tab inspectionTab;

    @FXML
    private TextField kilometerField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField licenseNumberField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TableView<Rental> carTableView;

    @FXML
    private TableColumn<Rental, String> brandColumn;

    @FXML
    private TableColumn<Rental, String> customerNameColumn;

    @FXML
    private TableColumn<Rental, String> licensePlateColumn;

    @FXML
    private TableColumn<Rental, String> modelColumn;

    @FXML
    private TableColumn<Rental, Double> priceColumn;

    @FXML
    private TableColumn<Rental, String> rentalDateColumn, returnDateColumn;

    @FXML
    private Button registerButton;

    @FXML
    private TextField registeredCarField;

    @FXML
    private TextField registeredCustomerField;

    @FXML
    private CheckBox registrationCheck;

    @FXML
    private DatePicker rentalDatePicker;

    @FXML
    private Tab rentalFinalizationTab;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private FontAwesomeIconView searchButton;

    @FXML
    private Button searchCar;

    @FXML
    private CheckBox spareTyreCheck;

    @FXML
    private TabPane tabPane;

    @FXML
    private CheckBox toolSetCheck;

    @FXML
    private TextField totalPriceField;
    private Rental selectedRentedCar;

    @FXML
    public void initialize() {

/*      carSelectionTab.setDisable(true);
        customerRegistrationTab.setDisable(false);
        inspectionTab.setDisable(false);
        rentalFinalizationTab.setDisable(false);*/

        loadRentedCars();

        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty
                (cellData.getValue().getCustomer().getFirstName() + " " +
                        (cellData.getValue().getCustomer().getLastName().length() >= 2
                                ? cellData.getValue().getCustomer().getLastName().substring(0, 2)
                                : cellData.getValue().getCustomer().getLastName())));

        licensePlateColumn.setCellValueFactory(cellData -> new SimpleStringProperty
                (cellData.getValue().getCar().getLicensePlate()));

        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCar().getBrand()));

        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCar().getModel()));

        rentalDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFormattedRentalDate()));

        returnDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFormattedReturnDate()));
    }

    private void loadRentedCars() {
        List<Rental> rentedCars = rentalService.getRentedCars();

        if (rentedCars != null) {
            ObservableList<Rental> rentalObservableList = FXCollections.observableArrayList(rentedCars);
            carTableView.setItems(rentalObservableList);
        }

    }

    @FXML
    void findRentedCarByPlate(ActionEvent event) {
        String licensePlate = registeredCarField.getText().trim();
        if (!licensePlate.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "UYARI", "Lütfen Plaka Girinizi!");
            return;
        }

        Rental rental = rentalService.findRentedInfoByPlate(licensePlate, null);

        if (rental != null) {
            selectedRentedCar = rental;

        }
    }

    @FXML
    void registerCustomer(ActionEvent event) {

    }

    @FXML
    void findCustomerByLicense(MouseEvent event) {

    }


    @FXML
    void saveInspection(ActionEvent event) {

    }

    @FXML
    void finalizeRental(ActionEvent event) {

    }

}
