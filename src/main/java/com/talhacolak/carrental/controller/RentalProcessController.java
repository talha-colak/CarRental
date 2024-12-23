package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.dto.CarStatus;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.talhacolak.carrental.service.AlertUtil.showAlert;

public class RentalProcessController {

    @FXML
    public Label firstNameLabel, lastNameLabel, phoneNumberLabel, emailLabel, licenseNumberLabel;

    @FXML
    FontAwesomeIconView searchButton;

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
    private ComboBox<String> rentalDateBox, returnDateBox = new ComboBox<>();

    @FXML
    private Button registerButton, inspectionButton, finalizeButton, searchCar;

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

/*        customerRegistrationTab.setOnSelectionChanged(event -> {
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
        });*/

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
            setInspectionFields();
        }
        if (isInspectionedCompleted) {
            resetRentalFields();
            rentalFinalizationTab.setDisable(false);
            populateComboBox();
        }

    }

    private void populateCarTableView() {
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        inspectionColumn.setCellValueFactory(cellData -> {
            Car car = cellData.getValue();
            LocalDateTime recentInspectionDate = car.getInspectionList().stream()
                    .map(Inspection::getInspectionDate)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);
            return new SimpleObjectProperty<>(recentInspectionDate);
        });

        ObservableList<Car> carObservableList = FXCollections.observableArrayList(carService.getAllCars());
        carTableView.setItems(carObservableList);
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
            setInspectionFields();

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
//            setInspectionItems();
            setInspectionFields();

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

        if (totalPriceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Toplam Fiyatı Girin!");
            return;
        }

        if (rentalDatePicker.getValue() == null || returnDatePicker.getValue() == null ||
                rentalDateBox.getValue() == null || returnDateBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen Tarih ve Saat Seçiniz!");
            return;
        }

        LocalDate rentalDate = rentalDatePicker.getValue();
        LocalTime rentalTime = LocalTime.parse(rentalDateBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime rentalDateTime = LocalDateTime.of(rentalDate, rentalTime);

        LocalDate returnDate = returnDatePicker.getValue();
        LocalTime returnTime = LocalTime.parse(returnDateBox.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime returnDateTime = LocalDateTime.of(returnDate, rentalTime);

        if (rentalDate.isAfter(returnDate) || (rentalDate.isEqual(returnDate) && rentalTime.isAfter(returnTime))) {
            showAlert(Alert.AlertType.ERROR, "Hata", "İade Zamanı Kiralamadan Sonra Olmalıdır!");
            return;
        }

        Rental rental = new Rental();
        rental.setCar(selectedCar);
        rental.setCustomer(selectedCustomer);
        rental.setBeforeInspection(completedInspection);
        rental.setRentalDate(rentalDateTime);
        rental.setReturnDate(returnDateTime);
        rental.setTotalPrice(Double.parseDouble(totalPriceField.getText()));
        rental.setRentalStatus(RentalStatus.ONGOING);

        selectedCar.setStatus(CarStatus.RENTED);
        carService.modifyCarStatus(selectedCar);

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

    private void setInspectionItems() {

        //TODO
        if (isCustomerSelected) {
            spareTyreCheck.setSelected(true);
            floorMatCheck.setSelected(true);
            registrationCheck.setSelected(true);
            aerialCheck.setSelected(true);
            babySeatCheck.setSelected(true);
            firstAidKitCheck.setSelected(true);
            toolSetCheck.setSelected(true);
            fireExtinguisherCheck.setSelected(true);

            kilometerField.setText("0");
            fuelSlider.setValue(8);
            descriptionField.setText(selectedCustomer.getFirstName().concat
                    (" " + selectedCustomer.getLastName().substring(0, 2))
                    + " " + selectedCar.getBrand() + " " + selectedCar.getModel());
        } else {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Sorun Var!");
        }
    }

    private void setInspectionFields() {
        if (selectedCar == null || selectedCar.getInspectionList().isEmpty()) {
            setInspectionItems();
            return;
        }

        Inspection inspection = selectedCar.getInspectionList().get(0);
        if (!inspectionTab.isDisable()) {

            floorMatCheck.setSelected(inspection.getFloorMat() != null && inspection.getFloorMat());
            spareTyreCheck.setSelected(inspection.getSpareTyre() != null && inspection.getSpareTyre());
            fireExtinguisherCheck.setSelected(inspection.getFireExtinguisher() != null && inspection.getFireExtinguisher());
            toolSetCheck.setSelected(inspection.getToolSet() != null && inspection.getToolSet());
            aerialCheck.setSelected(inspection.getAerial() != null && inspection.getAerial());
            firstAidKitCheck.setSelected(inspection.getFirstAidKit() != null && inspection.getFirstAidKit());
            registrationCheck.setSelected(inspection.getRegistration() != null && inspection.getRegistration());
            babySeatCheck.setSelected(inspection.getBabySeat() != null && inspection.getBabySeat());

            kilometerField.setText(inspection.getKilometer() != null ? String.valueOf(inspection.getKilometer()) : "");
            fuelSlider.setValue(inspection.getFuelStatus() != null ? inspection.getFuelStatus() : 0);
            descriptionField.setText(inspection.getDescription() != null ? inspection.getDescription() : "");
        } else {
            setInspectionItems();
        }
    }

    /*
    private void setInspectionFields() {
        //TODO
        Inspection inspection = new Inspection();
        if (!inspectionTab.isDisable()) {
            floorMatCheck.setSelected(inspection.getFloorMat() != null && inspection.getFloorMat());
            spareTyreCheck.setSelected(inspection.getSpareTyre() != null && inspection.getSpareTyre());
            fireExtinguisherCheck.setSelected(inspection.getFireExtinguisher() != null && inspection.getFireExtinguisher());
            toolSetCheck.setSelected(inspection.getToolSet() != null && inspection.getToolSet());
            aerialCheck.setSelected(inspection.getAerial() != null && inspection.getAerial());
            firstAidKitCheck.setSelected(inspection.getFirstAidKit() != null && inspection.getFirstAidKit());
            registrationCheck.setSelected(inspection.getRegistration() != null && inspection.getRegistration());
            babySeatCheck.setSelected(inspection.getBabySeat() != null && inspection.getBabySeat());

            kilometerField.setText(inspection.getKilometer() != null ? String.valueOf(inspection.getKilometer()) : "");
            fuelSlider.setValue(inspection.getFuelStatus() != null ? inspection.getFuelStatus() : 0);
            descriptionField.setText(inspection.getDescription() != null ? inspection.getDescription() : "");
        } else {
            setInspectionItems();
        }
    }*/

    private void populateComboBox() {
        generateTimeItems();

        rentalDateBox.setValue("08:30");
        rentalDateBox.setValue("09:00");

        returnDateBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalTime rentalTime = null;
            if (newValue != null && rentalDateBox.getValue() != null) {
                rentalTime = LocalTime.parse(rentalDateBox.getValue());
                LocalTime returnTime = LocalTime.parse(newValue);

                if (!returnTime.isAfter(rentalTime)) {
                    showAlert(Alert.AlertType.WARNING, "Geçersiz Zaman", "İade Zamanı Kiralama Zamanından Sonra Olmalıdır!");
                    returnDateBox.setValue(rentalTime.plusMinutes(30).format(DateTimeFormatter.ofPattern("HH:mm")));
                }
            }
        });
    }

    private void generateTimeItems() {
        ObservableList<String> timeSlots = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(8, 30);
        LocalTime endTime = LocalTime.of(18, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (!startTime.isAfter(endTime)) {
            timeSlots.add(startTime.format(formatter));
            startTime = startTime.plusMinutes(30);
        }
        rentalDateBox.setItems(timeSlots);
        returnDateBox.setItems(timeSlots);
    }
}
