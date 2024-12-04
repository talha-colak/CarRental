package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.entity.Customer;
import com.talhacolak.carrental.entity.Inspection;
import com.talhacolak.carrental.entity.Rental;
import com.talhacolak.carrental.service.CarInspectionService;
import com.talhacolak.carrental.service.CarService;
import com.talhacolak.carrental.service.CustomerService;
import com.talhacolak.carrental.service.RentalService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

import static com.talhacolak.carrental.service.AlertUtil.showAlert;

public class RentalProcessController {
    public Label firstNameLabel, lastNameLabel, phoneNumberLabel, emailLabel, licenseNumberLabel;
    @FXML
    private Button nextTabButton, deleteCustomerButton, deleteInspectionButton,
            refreshRentalButton, deleteRentalButton;
    @FXML
    FontAwesomeIconView searchButton;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab carSelectionTab, customerRegistrationTab, inspectionTab, rentalFinalizationTab;
    @FXML
    private TextField licenseNumberField, firstNameField, lastNameField,
            phoneNumberField, emailField, madeInspectionField, registeredCustomerField, registeredCarField;

    @FXML
    private ListView customerListView, inspectionListView, carListView;

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, String> licensePlateColumn, brandColumn, modelColumn, statusColumn;

    @FXML
    private TableColumn<Car, Integer> priceColumn;

    @FXML
    private TextField kilometerField, descriptionField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private DatePicker rentalDatePicker, returnDatePicker;

    @FXML
    private CheckBox floorMatCheck, aerialCheck, firstAidKitCheck, fireExtinguisherCheck,
            spareTyreCheck, babySeatCheck, registrationCheck, toolSetCheck;

    @FXML
    private Slider fuelSlider;

    @FXML
    private Button registerButton, inspectionButton, finalizeButton,
            refreshInspectionButton, refreshCustomerButton;

    private final CarService carService = new CarService();
    private final CustomerService customerService = new CustomerService();
    private final CarInspectionService inspectionService = new CarInspectionService();

    private final ObservableList<String> customerObservableList = FXCollections.observableArrayList();
    private final ObservableList<String> inspectionObservableList = FXCollections.observableArrayList();

    private Car selectedCar;
    private Customer selectedCustomer;
    private Inspection completedInspection;

    private boolean isCarSelected = false;
    private boolean isCustomerSelected = false;
    private boolean isInspectiodCompleted = false;
    private boolean isRentalFinalized = false;

    @FXML
    public void initialize() {

        carSelectionTab.setDisable(false);
        customerRegistrationTab.setDisable(true);
        inspectionTab.setDisable(true);
        rentalFinalizationTab.setDisable(true);

        customerRegistrationTab.setOnSelectionChanged(event -> {
            if (!isCarSelected) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "Araba Seç", "Bir Sonraki Aşamaya Geçmek" +
                        " İçin Araba Seçmek Gerekmektedir!");
            }
        });

        inspectionTab.setOnSelectionChanged(event -> {
            if (!isCustomerSelected) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "Müşteri Seç", "Bir Sonraki Aşamaya Geçmek" +
                        " İçin Müşteri Seçmek Gerekmektedir!");
            }
        });

        rentalFinalizationTab.setOnSelectionChanged(event -> {
            if (!isInspectiodCompleted) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "İncelemeyi Tamamla", "Bir Sonraki Aşamaya Geçmek" +
                        " İçin İncelemeyi Tamamlamak Gerekmektedir!");
            }
        });

        carTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedCar = newValue;
                isCarSelected = true;
                enableNextPhase();
            }
        });

//      refreshCustomerList();
        refreshInspectionList();
        populateCarTableView();
    }

    private void enableNextPhase() {
        if (isCarSelected) {
            customerRegistrationTab.setDisable(false);
        }
        if (isCustomerSelected) {
            inspectionTab.setDisable(false);
        }
        if (isInspectiodCompleted) {
            rentalFinalizationTab.setDisable(false);
        }
    }

    private void populateCarTableView() {
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Car> carObservableList = FXCollections.observableArrayList(carService.getAllCars());
        carTableView.setItems(carObservableList);

/*        carTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedCar = newValue;
            }
        });*/
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
        Customer newcustomer = new Customer();
        newcustomer.setLicenseNumber(licenseNumberField.getText());
        newcustomer.setFirstName(firstNameField.getText());
        newcustomer.setLastName(lastNameField.getText());
        newcustomer.setPhoneNumber(phoneNumberField.getText());
        newcustomer.setEmail(emailField.getText());

        //TODO: Müşteri bilgileri kaydedilecek ve Bir sonraki sekme açılacak!!
        try {
            customerService.save(newcustomer);
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müşteri Başarıyla Kaydedildi!");
            clearCustomerField();

            licenseNumberLabel.setText("Sicil Numarası: " + newcustomer.getLicenseNumber());
            firstNameLabel.setText("Ad: " + newcustomer.getFirstName());
            lastNameLabel.setText("Soyad: " + newcustomer.getLastName());
            phoneNumberLabel.setText("Telefon: " + newcustomer.getPhoneNumber());
            emailLabel.setText("E-posta: " + newcustomer.getEmail());

            selectedCustomer = newcustomer;
            isCustomerSelected = true;
            enableNextPhase();
//          refreshCustomerList();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri Kaydedilemedi: " + e.getMessage());
        }
    }

    @FXML
    private void findCustomerByLicense() {

        String licenseNumber = registeredCustomerField.getText().trim();
        if (licenseNumber.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen Ehliyet Sicil Numarası Giriniz!");
            return;
        }

        Customer customer = customerService.findCustomerById(licenseNumber);
        if (customer != null) {
            selectedCustomer = customer;
            firstNameLabel.setText("Ad: " + customer.getFirstName());
            lastNameLabel.setText("Soyad: " + customer.getLastName());
            phoneNumberLabel.setText("Telefon: " + customer.getPhoneNumber());
            emailLabel.setText("E-posta: " + customer.getEmail());
            isCustomerSelected = true;
            enableNextPhase();
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müşteri Bulundu!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Başarısız", "Müşteri Bulunamadı!");
        }

        /*
        if (registeredCustomerField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Lisans Numaranızı Girin!");
        }

        Customer customer = customerService.findCustomerById(registeredCustomerField.getText());

        if (customer != null) {
            showAlert(Alert.AlertType.INFORMATION, "Müşteri Bulundu!", customer.toString());
        } else {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri Bulunamadı!");
        }
        */

    }

/*    @FXML
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
    }*/

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
            showAlert(Alert.AlertType.ERROR, "Hata", "İnceleme bilgileri kaydedilemedi: ");
            System.err.println(e.getMessage());
            e.printStackTrace();
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

        if (selectedCar == null) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Bir Araba Seçin!");
            return;
        }

        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Bir Müşteri Seçin!");
            return;
        }

        if (rentalDatePicker.getValue() == null ||
                returnDatePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Kiralama Ve Geri Dönüş" +
                    " Tarihlerini Seçin!");
            return;
        }

        if (totalPriceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Toplam Fiyatı Girin!");
            return;
        }

        Rental rental = new Rental();
        rental.setCar(selectedCar);
        rental.setCustomer(selectedCustomer);
        rental.setRentalDate(rentalDatePicker.getValue().atStartOfDay());
        rental.setReturnDate(returnDatePicker.getValue().atStartOfDay());
        rental.setTotalPrice(Double.parseDouble(totalPriceField.getText()));

        //TODO: Kiralama bilgileri kaydedilip işlem sonlanacak!!

        try {

            RentalService rentalService = new RentalService();
            rentalService.save(rental);

            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Kiralama İşlemi Başarıyla Tamamlandı!");

            resetRentalFields();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Kiralama İşlemi Sırasında Hata Oluştu" + e.getMessage());
        }
    }

    private void resetRentalFields() {
        rentalDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(null);
        totalPriceField.clear();
        isCarSelected = false;
        isCustomerSelected = false;
        isInspectiodCompleted = false;
        isRentalFinalized = false;

        enableNextPhase();

//        carSelectionTab.setDisable(false);
//        customerRegistrationTab.setDisable(true);
//        inspectionTab.setDisable(true);
//        rentalFinalizationTab.setDisable(true);

    }

    @FXML
    private void refreshCarList() {

    }
}