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
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private ComboBox languageSelector;

    @FXML
    private BorderPane login_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel, title1, title2, title3;

    @FXML
    private Button loginButton, close;

    private ResourceBundle bundle;
    private Locale currentLocale;

    @Getter
    private static User loggedInUser;

    @FXML
    public void initialize() {

        setLanguage(new Locale("tr", "TR"));

        languageSelector.getItems().addAll("Türkçe", "English");
        languageSelector.setValue("Türkçe");

        languageSelector.setOnAction(event -> {
            if ("English".equals(languageSelector.getValue())) {
                setLanguage(new Locale("en", "EN"));
            } else {
                setLanguage(new Locale("tr", "TR"));
            }
        });
    }

    private void setLanguage(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("languages.crs_Localization", currentLocale);

        loginButton.setText(bundle.getString("login.form.loginButtonLabel"));
        username.setPromptText(bundle.getString("login.form.usernameLabel"));
        password.setPromptText(bundle.getString("login.form.passwordLabel"));

        title1.setText(bundle.getString("login.form.title1"));
        title2.setText(bundle.getString("login.form.title2"));
        title3.setText(bundle.getString("login.form.title3"));

    }

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
            errorLabel.setText(bundle.getString("login.form.errorMessageLabel"));
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.createQuery("FROM User WHERE userName =" + " :username", User.class).setParameter("username", usernameInput).uniqueResult();
            transaction.commit();

            if (user != null && BCrypt.checkpw(passwordInput, user.getPassword())) {
                loggedInUser = user;
                navigateToDashboard();

            } else {
                errorLabel.setText(bundle.getString("login.form.errorMessageLabel2"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText(bundle.getString("login.form.errorMessageLabel3"));
        }
    }

    private void navigateToDashboard() {

        try {
            Stage currentStage = (Stage) login_form.getScene().getWindow();
            Scene dashboardScene = CarRentalApplication.loadscene("dashboard.fxml", 1280, 720, currentLocale);
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