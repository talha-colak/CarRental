/**
 * Sample Skeleton for 'rental-retrieve.fxml' Controller Class
 */

package com.talhacolak.carrental.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RentalFinalizationController {

    @FXML // fx:id="aerialCheck"
    private CheckBox aerialCheck; // Value injected by FXMLLoader

    @FXML // fx:id="babySeatCheck"
    private CheckBox babySeatCheck; // Value injected by FXMLLoader

    @FXML // fx:id="brandColumn"
    private TableColumn<?, ?> brandColumn; // Value injected by FXMLLoader

    @FXML // fx:id="carSelectionTab"
    private Tab carSelectionTab; // Value injected by FXMLLoader

    @FXML // fx:id="carTableView"
    private TableView<?> carTableView; // Value injected by FXMLLoader

    @FXML // fx:id="customerNameColumn"
    private TableColumn<?, ?> customerNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="customerRegistrationTab"
    private Tab customerRegistrationTab; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextField descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private TextField emailField; // Value injected by FXMLLoader

    @FXML // fx:id="finalizeButton"
    private Button finalizeButton; // Value injected by FXMLLoader

    @FXML // fx:id="fireExtinguisherCheck"
    private CheckBox fireExtinguisherCheck; // Value injected by FXMLLoader

    @FXML // fx:id="firstAidKitCheck"
    private CheckBox firstAidKitCheck; // Value injected by FXMLLoader

    @FXML // fx:id="firstNameField"
    private TextField firstNameField; // Value injected by FXMLLoader

    @FXML // fx:id="floorMatCheck"
    private CheckBox floorMatCheck; // Value injected by FXMLLoader

    @FXML // fx:id="fuelSlider"
    private Slider fuelSlider; // Value injected by FXMLLoader

    @FXML // fx:id="inspectionButton"
    private Button inspectionButton; // Value injected by FXMLLoader

    @FXML // fx:id="inspectionTab"
    private Tab inspectionTab; // Value injected by FXMLLoader

    @FXML // fx:id="kilometerField"
    private TextField kilometerField; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameField"
    private TextField lastNameField; // Value injected by FXMLLoader

    @FXML // fx:id="licenseNumberField"
    private TextField licenseNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="licensePlateColumn"
    private TableColumn<?, ?> licensePlateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="modelColumn"
    private TableColumn<?, ?> modelColumn; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberField"
    private TextField phoneNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="priceColumn"
    private TableColumn<?, ?> priceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="registerButton"
    private Button registerButton; // Value injected by FXMLLoader

    @FXML // fx:id="registeredCarField"
    private TextField registeredCarField; // Value injected by FXMLLoader

    @FXML // fx:id="registeredCustomerField"
    private TextField registeredCustomerField; // Value injected by FXMLLoader

    @FXML // fx:id="registrationCheck"
    private CheckBox registrationCheck; // Value injected by FXMLLoader

    @FXML // fx:id="rentalDatePicker"
    private DatePicker rentalDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="rentalFinalizationTab"
    private Tab rentalFinalizationTab; // Value injected by FXMLLoader

    @FXML // fx:id="rentaltimeColumn"
    private TableColumn<?, ?> rentaltimeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="returnDatePicker"
    private DatePicker returnDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private FontAwesomeIconView searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="searchCar"
    private Button searchCar; // Value injected by FXMLLoader

    @FXML // fx:id="spareTyreCheck"
    private CheckBox spareTyreCheck; // Value injected by FXMLLoader

    @FXML // fx:id="tabPane"
    private TabPane tabPane; // Value injected by FXMLLoader

    @FXML // fx:id="toolSetCheck"
    private CheckBox toolSetCheck; // Value injected by FXMLLoader

    @FXML // fx:id="totalPriceField"
    private TextField totalPriceField; // Value injected by FXMLLoader

    @FXML
    void finalizeRental(ActionEvent event) {

    }

    @FXML
    void findCarByPlate(ActionEvent event) {

    }

    @FXML
    void findCustomerByLicense(MouseEvent event) {

    }

    @FXML
    void registerCustomer(ActionEvent event) {

    }

    @FXML
    void saveInspection(ActionEvent event) {

    }

}