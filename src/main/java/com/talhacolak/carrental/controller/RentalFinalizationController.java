package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.dto.CarStatus;
import com.talhacolak.carrental.dto.RentalStatus;
import com.talhacolak.carrental.entity.Customer;
import com.talhacolak.carrental.entity.Inspection;
import com.talhacolak.carrental.entity.Rental;
import com.talhacolak.carrental.service.CarInspectionService;
import com.talhacolak.carrental.service.RentalService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
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

    @FXML
    private ComboBox<String> rentalDateBox, returnDateBox;

    private Rental selectedRental;
    private Boolean isRentalSelected = false;

    private Customer selectedCustomer;
    private Boolean isCustomerSelected = false;

    private Inspection completedInspection;
    private Boolean isInspectionCompleted = false;

    private Rental finalizedRental;
    private Boolean isRentalFinalized = false;

    @FXML
    public void initialize() {

        carSelectionTab.setDisable(false);
        customerRegistrationTab.setDisable(true);
        inspectionTab.setDisable(true);
        rentalFinalizationTab.setDisable(true);

        loadRentedCars();

        carTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedRental = newValue;
                isRentalSelected = true;
                enableNextPhase();
            }
        });

        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + (cellData.getValue().getCustomer().getLastName().length() >= 2 ? cellData.getValue().getCustomer().getLastName().substring(0, 2) : cellData.getValue().getCustomer().getLastName())));

        licensePlateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getLicensePlate()));

        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getBrand()));

        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getModel()));

        rentalDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedRentalDate()));

        returnDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedReturnDate()));

        customerRegistrationTab.setOnSelectionChanged(event -> {

            if (customerRegistrationTab.isSelected() && selectedRental != null) {
                populateCustomerFields();
            }
        });
    }

    private void loadRentedCars() {
        List<Rental> rentedCars = rentalService.getRentedCars();

        if (rentedCars != null) {
            ObservableList<Rental> rentalObservableList = FXCollections.observableArrayList(rentedCars);
            carTableView.setItems(rentalObservableList);
        }
    }

    private void enableNextPhase() {
        if (isRentalSelected) {
            customerRegistrationTab.setDisable(false);
        }
        if (isCustomerSelected) {
            inspectionTab.setDisable(false);
        }
        if (isInspectionCompleted) {
            rentalFinalizationTab.setDisable(false);
            populateRentalItems();
        }
        if (isRentalFinalized) {
//            selectedRental.setRentalStatus(RentalStatus.FINISHED);
//            selectedRental.getCar().setStatus(CarStatus.AVAILABLE);

            showAlert(Alert.AlertType.INFORMATION, "Tebrikler", "Araç Teslim Alındı");
        }

    }

    @FXML
    private void findRentedCarByPlate(ActionEvent event) {
        String licensePlate = registeredCarField.getText().trim();
        if (licensePlate.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "UYARI", "Lütfen Plaka Girinizi!");
            return;
        }

        Rental rental = rentalService.findRentedInfoByPlate(licensePlate);
        if (rental != null) {
            selectedRental = rental;
            isRentalSelected = true;
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Araba Seçildi");
        } else {
            showAlert(Alert.AlertType.ERROR, "Başarısız", "Araba Seçilemedi");
        }
    }

    @FXML
    private void registerCustomer(ActionEvent event) {
        enableNextPhase();
        setInspectionFields();
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(inspectionTab);
    }

    private void populateCustomerFields() {

        Customer customer = rentalService.getCustomerByCar(selectedRental, RentalStatus.ONGOING);

        licenseNumberField.setText(customer.getLicenseNumber());
        lastNameField.setEditable(false);

        firstNameField.setText(customer.getFirstName());
        firstNameField.setEditable(false);

        lastNameField.setText(customer.getLastName());
        lastNameField.setEditable(false);

        phoneNumberField.setText(customer.getPhoneNumber());
        phoneNumberField.setEditable(false);

        emailField.setText(customer.getEmail());
        emailField.setEditable(false);

        selectedCustomer = customer;
        isCustomerSelected = true;
    }

    @FXML
    private void saveInspection(ActionEvent event) {
        Inspection inspection = new Inspection();

        inspection.setKilometer(Integer.parseInt(kilometerField.getText()));
        inspection.setFuelStatus((int) fuelSlider.getValue());
        inspection.setDescription(descriptionField.getText());
        inspection.setInspectionDate(LocalDateTime.now());

        inspection.setFloorMat(floorMatCheck.isSelected());
        inspection.setRegistration(registrationCheck.isSelected());
        inspection.setBabySeat(babySeatCheck.isSelected());
        inspection.setAerial(aerialCheck.isSelected());
        inspection.setToolSet(toolSetCheck.isSelected());
        inspection.setFireExtinguisher(fireExtinguisherCheck.isSelected());
        inspection.setFirstAidKit(firstAidKitCheck.isSelected());
        inspection.setSpareTyre(spareTyreCheck.isSelected());

        selectedRental.setAfterInspection(inspection);
        selectedRental.getCar().getInspectionList().add(inspection);

        try {
            CarInspectionService carInspectionService = new CarInspectionService();
            carInspectionService.saveInspectionWithCar(selectedRental, inspection);

            isInspectionCompleted = true;
            completedInspection = inspection;

            enableNextPhase();
//          populateRentalItems();

            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(rentalFinalizationTab);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "İnceleme Bilgileri Kaydedilemedi!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void setInspectionFields() {
        if (selectedRental == null || selectedRental.getCar().getInspectionList().isEmpty()) {
            setInspectionItems();
            return;
        }

        Inspection inspection = selectedRental.getBeforeInspection();
        if (!inspectionTab.isDisable()) {

            floorMatCheck.setSelected(inspection.getFloorMat() != null && inspection.getFloorMat());
            aerialCheck.setSelected(inspection.getAerial() != null && inspection.getAerial());
            spareTyreCheck.setSelected(inspection.getSpareTyre() != null && inspection.getSpareTyre());
            babySeatCheck.setSelected(inspection.getBabySeat() != null && inspection.getBabySeat());
            firstAidKitCheck.setSelected(inspection.getFirstAidKit() != null && inspection.getFirstAidKit());
            fireExtinguisherCheck.setSelected(inspection.getFireExtinguisher() != null && inspection.getFireExtinguisher());
            registrationCheck.setSelected(inspection.getRegistration() != null && inspection.getRegistration());
            toolSetCheck.setSelected(inspection.getToolSet() != null && inspection.getToolSet());

            kilometerField.setText(inspection.getKilometer() != null ? String.valueOf(inspection.getKilometer()) : "");
            fuelSlider.setValue(inspection.getFuelStatus() != null ? inspection.getFuelStatus() : 0);
            descriptionField.setText(inspection.getDescription() != null ? inspection.getDescription() : "");
        } else {
            setInspectionItems();
        }

    }

    private void setInspectionItems() {
        if (isCustomerSelected) {
            spareTyreCheck.setSelected(true);
            floorMatCheck.setSelected(true);
            registrationCheck.setSelected(true);
            aerialCheck.setSelected(true);
            babySeatCheck.setSelected(true);
            firstAidKitCheck.setSelected(true);
            fireExtinguisherCheck.setSelected(true);
            toolSetCheck.setSelected(true);
            kilometerField.setText("0");
            fuelSlider.setValue(8);

            descriptionField.setText(selectedCustomer.getFirstName().concat
                    (" " + selectedCustomer.getLastName().substring(0, 2))
                    + " " + selectedRental.getCar().getBrand() + " " + selectedRental.getCar().getModel());
        } else {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Sorun Var!");
        }
    }

    @FXML
    private void finalizeRental(ActionEvent event) {
        try {

            selectedRental.setTotalPrice(Integer.valueOf(totalPriceField.getText()));
            selectedRental.setRentalStatus(RentalStatus.FINISHED);
            selectedRental.getCar().setStatus(CarStatus.AVAILABLE);

            rentalService.updateRental(selectedRental);

            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Araç Teslim Alındı ve Kiralama Tamamlandı.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Kiralama Tamamlanırken Hata Oluştu!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateRentalItems() {
        rentalDatePicker.setValue(selectedRental.getRentalDate().toLocalDate());
        rentalDateBox.setValue(String.valueOf(selectedRental.getRentalDate().toLocalTime()));
        returnDatePicker.setValue(selectedRental.getReturnDate().toLocalDate());
        returnDateBox.setValue(String.valueOf(selectedRental.getReturnDate().toLocalTime()));

        rentalDatePicker.setEditable(false);
        rentalDateBox.setEditable(false);
        returnDateBox.setEditable(false);
        returnDatePicker.setEditable(false);
//        totalPriceField.setText(selectedRental.getTotalPrice().toString());

        LocalDateTime currentDateTime = LocalDateTime.now();
        long delayDays = java.time.Duration.between(selectedRental.getReturnDate(), currentDateTime).toDays();

        int delayPenalty = calculateDelayPenalty(delayDays);

        int inspectionPenalty = calculateInspectionPenalty();

        int totalPenalty = delayPenalty + inspectionPenalty;
        int totalPrice = selectedRental.getTotalPrice() + totalPenalty;

        totalPriceField.setText(String.format("%d", totalPrice));
    }

    private int calculateDelayPenalty(long delayDays) {
        int delayPenalty = 0;
        if (delayDays > 0) {
            delayPenalty = (int) delayDays * 50;
        }
        return delayPenalty;
    }

    private int calculateInspectionPenalty() {
        int inspectionPenalty = 0;

        if (completedInspection != null) {
            inspectionPenalty += getMissingItems(completedInspection.getFireExtinguisher(), 20);
            inspectionPenalty += getMissingItems(completedInspection.getAerial(), 10);
            inspectionPenalty += getMissingItems(completedInspection.getRegistration(), 100);
            inspectionPenalty += getMissingItems(completedInspection.getBabySeat(), 50);
            inspectionPenalty += getMissingItems(completedInspection.getSpareTyre(), 80);
            inspectionPenalty += getMissingItems(completedInspection.getFloorMat(), 30);
            inspectionPenalty += getMissingItems(completedInspection.getFirstAidKit(), 60);
            inspectionPenalty += getMissingItems(completedInspection.getToolSet(), 100);
            inspectionPenalty += getMissingFuel(completedInspection.getFuelStatus(), 200);
        }
        return inspectionPenalty;
    }

    private int getMissingItems(boolean items, int penalty) {
        if (items) {
            return 0;
        } else {
            return penalty;
        }
    }

    private int getMissingFuel(int fuelStatus, int penalty) {
        if (fuelStatus < 8) {
            int missingFuel = (int) (fuelSlider.getMax() - fuelStatus);
            return missingFuel * penalty;
        }
        return 0;
    }

}