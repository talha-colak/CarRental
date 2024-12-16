package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.dto.RentalStatus;
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
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.talhacolak.carrental.service.AlertUtil.showAlert;

public class RentalProcessController {
    @FXML
    public Label firstNameLabel, lastNameLabel, phoneNumberLabel, emailLabel, licenseNumberLabel;

    @FXML
    FontAwesomeIconView searchButton, searchCar;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab carSelectionTab, customerRegistrationTab, inspectionTab, rentalFinalizationTab;

    @FXML
    private TextField licenseNumberField, firstNameField, lastNameField, phoneNumberField, emailField, registeredCustomerField, registeredCarField;

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, String> licensePlateColumn, brandColumn, modelColumn, statusColumn;

    @FXML
    private TableColumn<Car, LocalDateTime> inspectionColumn;

    @FXML
    private TableColumn<Car, Integer> priceColumn;

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
    private Button registerButton, inspectionButton, finalizeButton;

    private final CarService carService = new CarService();
    private final CustomerService customerService = new CustomerService();
    private final CarInspectionService inspectionService = new CarInspectionService();
    private final RentalService rentalService = new RentalService();

    private Car selectedCar;
    private Customer selectedCustomer;
    private Inspection completedInspection;
    private Rental finalizedRental;

    private boolean isCarSelected = false;
    private boolean isCustomerSelected = false;
    private boolean isInspectionedCompleted = false;
    private boolean isRentalFinalized = false;

    @FXML
    public void initialize() {

        carSelectionTab.setDisable(false);
        customerRegistrationTab.setDisable(true);
        inspectionTab.setDisable(true);
        rentalFinalizationTab.setDisable(true);

        /*----------------------------------START--------------------------------------*/
        //TODO
        if (inspectionTab.isSelected()) {
            spareTyreCheck.setSelected(true);
            floorMatCheck.setSelected(true);
            registrationCheck.setSelected(true);
            aerialCheck.setSelected(true);
            babySeatCheck.setSelected(true);
            firstAidKitCheck.setSelected(true);
            toolSetCheck.setSelected(true);
            fireExtinguisherCheck.setSelected(true);
            fuelSlider.setValue(8);
        } else {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Sorun Var!");
        }
        /*----------------------------------END--------------------------------------*/

        customerRegistrationTab.setOnSelectionChanged(event -> {
            if (!isCarSelected) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "Araba Seç", "Bir Sonraki Aşamaya Geçmek" + " İçin Araba Seçmek Gerekmektedir!");
            }
        });

        inspectionTab.setOnSelectionChanged(event -> {
            if (!isCustomerSelected) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "Müşteri Seç", "Bir Sonraki Aşamaya Geçmek" + " İçin Müşteri Seçmek Gerekmektedir!");
            }
        });

        rentalFinalizationTab.setOnSelectionChanged(event -> {
            if (!isInspectionedCompleted) {
                event.consume();
                showAlert(Alert.AlertType.WARNING, "İncelemeyi Tamamla", "Bir Sonraki Aşamaya Geçmek" + " İçin İncelemeyi Tamamlamak Gerekmektedir!");
            }
        });

        carTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedCar = newValue;
                isCarSelected = true;
                enableNextPhase();
            }
        });

        populateCarTableView();
    }

    private void enableNextPhase() {
        if (isCarSelected) {
            customerRegistrationTab.setDisable(false);
        }
        if (isCustomerSelected) {
            inspectionTab.setDisable(false);
        }
        if (isInspectionedCompleted) {
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

    }

    @FXML
    private void registerCustomer() {

        if (licenseNumberField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()) {
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

            licenseNumberField.setEditable(false);

            firstNameField.setText(newcustomer.getFirstName());
            firstNameField.setEditable(false);

            lastNameField.setText(newcustomer.getLastName());
            lastNameField.setEditable(false);

            phoneNumberField.setText(newcustomer.getPhoneNumber());
            phoneNumberField.setEditable(false);

            emailField.setText(newcustomer.getEmail());
            emailField.setEditable(false);

            selectedCustomer = newcustomer;
            isCustomerSelected = true;
            enableNextPhase();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri Kaydedilemedi: " + e.getMessage());
        }
    }

    @FXML
    private void findCarByPlate() {
        String licensePlate = registeredCarField.getText().trim();
        if (licensePlate.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen Plaka Giriniz!");
            return;
        }

        Car car = CarService.findCarByPlate(licensePlate);
        if (car != null) {
            selectedCar = car;
            isCarSelected = true;
            enableNextPhase();
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Araba Bulundu");
        } else {
            showAlert(Alert.AlertType.WARNING, "Başarısız", "Araba Bulunamadı");
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

            firstNameField.setText(customer.getFirstName());
            firstNameField.setEditable(false);

            lastNameField.setText(customer.getLastName());
            lastNameField.setEditable(false);

            phoneNumberField.setText(customer.getPhoneNumber());
            phoneNumberField.setEditable(false);

            emailField.setText(customer.getEmail());
            emailField.setEditable(false);

            isCustomerSelected = true;

            enableNextPhase();

            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müşteri Bulundu!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Başarısız", "Müşteri Bulunamadı!");
            registeredCustomerField.selectAll();
        }
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

        if (kilometerField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
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
        inspection.setInspectionDate(LocalDateTime.now());

        //TODO: Araç inceleme formundaki bilgiler kaydedilecek ve Bir sonraki sekme açılacak!!
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            inspectionService.save(session, inspection);

            selectedCar.getInspectionList().add(inspection);
            session.merge(selectedCar);

            transaction.commit();

            isInspectionedCompleted = true;
            completedInspection = inspection;
            enableNextPhase();

/*            Inspection savedInspection = inspectionService.save(session, inspection);


            if (savedInspection != null) {
                Car car = session.merge(selectedCar);

                session.merge(car);
                transaction.commit();

                isInspectionedCompleted = true;
                completedInspection = savedInspection;
                enableNextPhase();

                showAlert(Alert.AlertType.INFORMATION, "Başarılı", "İnceleme bilgileri başarıyla kaydedildi");
                System.out.println("Worked " + savedInspection);
            } else {
                transaction.rollback();
                throw new RuntimeException("kaydedilen inceleme bilgileri NULL!!");
            }*/
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "İnceleme bilgileri kaydedilemedi: ");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
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

        if (rentalDatePicker.getValue() == null || returnDatePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Kiralama Ve Geri Dönüş" + " Tarihlerini Seçin!");
            return;
        }

        if (totalPriceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Toplam Fiyatı Girin!");
            return;
        }

        Rental rental = new Rental();
        rental.setCar(selectedCar);
        rental.setCustomer(selectedCustomer);
        rental.setBeforeInspection(completedInspection);
        rental.setRentalDate(rentalDatePicker.getValue().atStartOfDay());
        rental.setReturnDate(returnDatePicker.getValue().atStartOfDay());
        rental.setTotalPrice(Double.parseDouble(totalPriceField.getText()));
        rental.setRentalStatus(RentalStatus.ONGOING);


        //TODO: Kiralama bilgileri kaydedilip işlem sonlanacak!!

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            rentalService.save(session, rental);

            isRentalFinalized = true;
            finalizedRental = rental;

            transaction.commit();
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Kiralama İşlemi Başarıyla Tamamlandı!");

            resetRentalFields();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Kiralama İşlemi Sırasında Hata Oluştu");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetRentalFields() {

        rentalDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(null);
        totalPriceField.clear();

        isCarSelected = false;
        isCustomerSelected = false;
        isInspectionedCompleted = false;
        isRentalFinalized = false;
    }

}