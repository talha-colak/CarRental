package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.Car;
import com.talhacolak.carrental.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {

    /* @FXML
     private Label errorLabel;

       public void validateLogin() {
           if (loginFailed) {
               errorLabel.setText("Geçersiz Kullanıcı Adı ve Parola");
           }

       }
     */

    @FXML
    private BorderPane login_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    private Button goNext;

    @FXML
    private Button loginButton, close;

    @FXML
    private void close() {
        Stage stage = (Stage) login_form.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goNext() throws IOException {

        Stage stage = (Stage) goNext.getScene().getWindow();
        stage.setScene(CarRentalApplication.loadscene("dashboard.fxml", 1280, 720));
        stage.setTitle("Dashboard");
        //stage.getScene().getWindow().centerOnScreen();
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

//        Car car = new Car();
//        car.setBrand("BMW");
//        car.setModel("M3");
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(car);
//        transaction.commit();
//        session.close();
    }

    //TODO: loginAction metodunu düzenle.
    @FXML
    private void loginAction() {

        String usernameInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            errorLabel.setText("Kullanıcı adı ve şifre giriniz!");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.createQuery("FROM User WHERE userName = :username", User.class).setParameter("username", usernameInput).uniqueResult();
            transaction.commit();

            if (user != null /*&& BCrypt.checkpw(passwordInput, user.getPassword())*/) {
                navigateToDashboard();
            } else {
                errorLabel.setText("Geçersiz kullanıcı adı veya şifre!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Bir hata oluştu. Tekrar deneyin.");
        }

    }

    private void navigateToDashboard() {

        try {
            Stage currentStage = (Stage) login_form.getScene().getWindow();
            Scene dashboardScene = CarRentalApplication.loadscene("dashboard.fxml", 1280, 720);
            //FXMLLoader loader = new FXMLLoader(CarRentalApplication.class.getResource("dashboard.fxml"));
            //Parent root =loader.load();
            setStage(dashboardScene);
            currentStage.close();

//            Stage dashboardStage = new Stage();
//            dashboardStage.setScene(dashboardScene);
//            dashboardStage.initStyle(StageStyle.DECORATED);
//            dashboardStage.setTitle("Dashboard");
//            dashboardStage.centerOnScreen();
//            dashboardStage.setResizable(false);
            currentStage.close();
            //DashboardController dashboardController = loader.getController();
            //dashboardController.setStage(stage);
            //stage.setScene(CarRentalApplication.loadscene("dashboard.fxml",1280,720));
            //Scene dashboardScene = new Scene(root,1280,720);
            //stage.setScene(dashboardScene);
            //stage.setTitle("Dashboard");
            // stage.centerOnScreen();
            // stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Dashboard yüklenemedi!");
        }
    }

    private void setStage(Scene scene) {
        // Create a new stage for the dashboard
        Stage dashboardStage = new Stage();

        // Set the scene for the new stage
        dashboardStage.setScene(scene);

        // Set properties for the dashboard stage before showing it
        dashboardStage.initStyle(StageStyle.TRANSPARENT); // You can change this style if needed
        dashboardStage.setTitle("Dashboard");
        dashboardStage.centerOnScreen(); // Optional: center the stage
        dashboardStage.setResizable(false); // Optional: make the stage resizable

        // Show the new stage
        dashboardStage.show();
    }
}