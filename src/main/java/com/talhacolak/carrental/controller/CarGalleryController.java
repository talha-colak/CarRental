package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class CarGalleryController {

    @FXML
    private ScrollPane carGalleryScrollPane;

    @FXML
    private TilePane carTilePane;

    @FXML
    FontAwesomeIconView carAdd;

    @FXML
    public void initialize() {
        loadCarGallery();
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
        carTile.setStyle("-fx-border-width: 1;" +
                " -fx-border-color: black; -fx-background-color: White;");
        carTile.setSpacing(10);
        carTile.setPrefSize(220.0, 300.0); //220,300 210,280

//      ImageView carImage = new ImageView(new Image(car.getImageUrl()));
        String imageUrl = car.getImageUrl() != null ? car.getImageUrl() : "file:///C:/Users/Talha Çolak/IdeaProjects/CarRentalSystem/src/main/resources/com/talhacolak/carrental/images/placeholder.jpg";
        ImageView carImage = new ImageView(new Image(imageUrl));
        carImage.setFitHeight(120); //120
        carImage.setFitWidth(200);  //200
        carImage.setPreserveRatio(true);

        Text brandText = new Text(car.getBrand());
        Text modelText = new Text(car.getModel());
        Text priceText = new Text(car.getPrice() + "TL/Günlük");

        Button viewButton = new Button("Details");
        viewButton.setOnAction(e -> showCarDetails(car));

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editCar(car));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteCar(car));

        HBox buttonBox = new HBox(10, viewButton, editButton, deleteButton);

        carTile.getChildren().addAll(carImage, brandText, modelText, priceText, buttonBox);
        return carTile;
    }

    private void showCarDetails(Car car) {
        System.out.println("Showing details for: " + car.getBrand() + " " + car.getModel());

    }

    private void editCar(Car car) {
        System.out.println("Editing car: " + car.getBrand() + " " + car.getModel());
    }

    private void deleteCar(Car car) {
        System.out.println("Deleting car: " + car.getBrand() + " " + car.getModel());
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Car");
        confirmAlert.setHeaderText("Are you sure you want to delete this car?");
        confirmAlert.setContentText(car.getBrand() + " " + car.getModel());

        // Check user confirmation
        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.remove(car); // Remove the car
                session.getTransaction().commit();

                // Remove from UI
                carTilePane.getChildren().clear();
                loadCarGallery();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error deleting car: " + e.getMessage());
            }
        }
    }

    private Stage carAddStage = null;

    @FXML
    private void carAdd(MouseEvent event) {
        if (carAddStage == null) {
            carAddStage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(CarRentalApplication.
                        class.getResource("car-add.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                carAddStage.setScene(scene);
                carAddStage.initStyle(StageStyle.UTILITY);
                carAddStage.setTitle("Car Adding Forms");
                carAddStage.show();
                carAddStage.setOnCloseRequest(e -> carAddStage = null);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            carAddStage.toFront();
        }
    }


}
