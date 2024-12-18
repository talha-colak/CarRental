package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.CarRentalApplication;
import com.talhacolak.carrental.config.HibernateUtil;
import com.talhacolak.carrental.entity.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {

    @FXML
    private BorderPane login_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    private Button goNext, loginButton, close;

    @Getter
    private static User loggedInUser;

    @FXML
    private void close() {
        Stage stage = (Stage) login_form.getScene().getWindow();
        stage.close();
    }

    //TODO: loginAction metodunu düzenle.
    @FXML
    private void loginAction() {

        String usernameInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            errorLabel.setText("Kullanıcı adı ve şifre giriniz!");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.createQuery("FROM User WHERE userName =" +
                    " :username", User.class).setParameter("username",
                    usernameInput).uniqueResult();
            transaction.commit();

            if (user != null && BCrypt.checkpw(passwordInput, user.getPassword())) {
                navigateToDashboard();
                loggedInUser = user;

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
            setStage(dashboardScene);
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Dashboard yüklenemedi!");
        }
    }

    private void setStage(Scene scene) {
        // dashboard için Stage oluşturur
        Stage dashboardStage = new Stage();

        // Stage'e yeni bir Scene ayarlar
        dashboardStage.setScene(scene);

        // dashboard Stage'i göstermeden önce özellik atamaları yapılır
        dashboardStage.initStyle(StageStyle.TRANSPARENT);
        dashboardStage.setTitle("Dashboard");
        dashboardStage.centerOnScreen();
        dashboardStage.setResizable(false);

        // Stage'i gösterir
        dashboardStage.show();
    }
}