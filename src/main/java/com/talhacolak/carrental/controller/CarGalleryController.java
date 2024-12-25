package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.service.AlertUtil;
import com.talhacolak.carrental.service.CarService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class CarGalleryController {

    @FXML
    private ScrollPane carGalleryScrollPane;

    @FXML
    private AnchorPane carAddTile;

    @FXML
    private TilePane carTilePane;

    @FXML
    FontAwesomeIconView carAdd;

    private CarService carService = new CarService();

    @FXML
    public void initialize() {
        refreshCarGallery();
    }

    private void loadCarGallery() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Car> cars = session.createQuery("from Car", Car.class).list();

            for (Car car : cars) {
                VBox carTile = createCarTile(car);
                carTilePane.getChildren().add(carTile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Arabaları getirirken hata oluştu: " + e.getMessage());
        }
    }

    private VBox createCarTile(Car car) {
        VBox carTile = new VBox();
        carTile.setStyle("-fx-border-width: 1; -fx-border-color: black; -fx-background-color: White; -fx-alignment: CENTER ");
        carTile.setSpacing(10);
        carTile.setPrefSize(220.0, 300.0); //220,300 210,280
        carTile.setAlignment(Pos.CENTER_LEFT);

        String imageUrl = car.getImageUrl() != null ? car.getImageUrl() : "file:///C:/Users/Talha Çolak/IdeaProjects/CarRentalSystem/src/main/resources/com/talhacolak/carrental/images/placeholder.jpg";
        ImageView carImage = new ImageView(new Image(imageUrl));
        carImage.setFitHeight(120); //120
        carImage.setFitWidth(200);  //200
        carImage.setPreserveRatio(true);
        carImage.setStyle("-fx-border-width: 1;-fx-border-color: black;");
        VBox.setMargin(carImage, new Insets(10, 5, 0, 5));

        Text brandText = new Text(car.getBrand());
        brandText.setStyle("-fx-font-weight: bold;");
        Text modelText = new Text(car.getModel());
        modelText.setStyle("-fx-font-weight: bold;");
        Text priceText = new Text(car.getPrice() + "TL/Günlük");
        priceText.setStyle("-fx-font-weight: bold;");

        Button viewButton = new Button("Details");
        viewButton.setOnAction(e -> showCarDetails(car));

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editCar(car));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteCar(car));

        HBox buttonBox = new HBox(viewButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(0, 0, 5, 5));
        buttonBox.setAlignment(Pos.CENTER);

        carTile.getChildren().addAll(carImage, brandText, modelText, priceText, buttonBox);
        return carTile;
    }

    private Stage detailsStage = null;

    private void showCarDetails(Car car) {
        System.out.println("Showing details for: " + car.getBrand() + " " + car.getModel());
        if (detailsStage == null) {
            detailsStage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("car-details.fxml"));
                Parent root = loader.load();

                CarDetailsController controller = loader.getController();
                controller.setCar(car);

                Scene scene = new Scene(root);
                detailsStage.setScene(scene);
                detailsStage.setTitle("Araba Detayları");
                detailsStage.initStyle(StageStyle.UTILITY);

                Window currentWindow = carGalleryScrollPane.getScene().getWindow();
                detailsStage.initOwner(currentWindow);
                detailsStage.initModality(Modality.WINDOW_MODAL);

                detailsStage.show();

                detailsStage.setOnCloseRequest(e -> {
                    detailsStage = null;
                    refreshCarGallery();
                });
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Yüklenemedi " + e.getMessage());
            }
        } else {
            detailsStage.toFront();
        }

    }

    private void editCar(Car car) {
        System.out.println("Araba Düzenleniyor: " + car.getBrand() + " " + car.getModel());
        try {
            FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("car-edit.fxml"));
            Parent root = loader.load();

            CarEditController controller = loader.getController();
            Stage stage = new Stage();
            controller.setCar(car, stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Araba Düzenleme");
            stage.initStyle(StageStyle.UTILITY);

            Window currentWindow = carGalleryScrollPane.getScene().getWindow();
            stage.initOwner(currentWindow);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();

            stage.setOnCloseRequest(e -> refreshCarGallery()); // Refresh the gallery on close
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Yüklenemedi: " + e.getMessage());
        }
    }

    private void deleteCar(Car car) {
        System.out.println("Deleting car: " + car.getBrand() + " " + car.getModel());

        if (carService.isCarRented(car)) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "Araba Silinemez", "Araba Kiralanmış",
                    "Bu Arabanın Kiralama Kaydı Var. Önce Onu Tamamlayın!!");

        } else {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Arabayı Sil");
            confirmAlert.setHeaderText("Bu Arabayı Silmek İstedğinizden Emin Misiniz?");
            confirmAlert.setContentText(car.getLicensePlate() + " " + car.getBrand() + " " + car.getModel());

            if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                carService.delete(car);
                refreshCarGallery();
            }
        }
    }

    private Stage carAddStage = null;

    @FXML
    private void carAdd(MouseEvent event) {
        if (carAddStage == null) {
            carAddStage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("car-add.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                carAddStage.setScene(scene);
                carAddStage.initStyle(StageStyle.UTILITY);

                Window currentWindow = carGalleryScrollPane.getScene().getWindow();
                carAddStage.initOwner(currentWindow);
                carAddStage.initModality(Modality.WINDOW_MODAL);

                carAddStage.setTitle("Araç Ekleme Formu");
                carAddStage.show();
                carAddStage.setOnCloseRequest(e -> {
                    carAddStage = null;
                    refreshCarGallery();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            carAddStage.toFront();
        }
    }

    private void refreshCarGallery() {
        carTilePane.getChildren().clear();
        carTilePane.getChildren().add(carAddTile);
        loadCarGallery();
    }
}
