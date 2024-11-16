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
    private Button loginButton;

    @FXML
    private Button close;

    @FXML
    private void close(){
        Stage stage = (Stage) login_form.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goNext() throws IOException {

        Stage stage = (Stage) goNext.getScene().getWindow();
        stage.setScene(CarRentalApplication.loadscene("dashboard.fxml",1280,720));
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

    @FXML
    private void loginaction() {

        String usernameInput = username.getText().trim();
        String passwordInput = password.getText().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()){
            errorLabel.setText("Kullanıcı adı ve şifre giriniz!");
        }

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.createQuery("FROM User WHERE userName = :username", User.class)
                    .setParameter("username",usernameInput)
                    .uniqueResult();
            transaction.commit();

            if (user != null /*&& BCrypt.checkpw(passwordInput, user.getPassword())*/) {
                navigateToDashboard();
            } else {
                errorLabel.setText("Geçersiz kullanıcı adı veya şifre!");
            }

        }
        catch(Exception e) {
                e.printStackTrace();
                errorLabel.setText("Bir hata oluştu. Tekrar deneyin.");
            }

    }

    private void navigateToDashboard(){

        try {
            Stage stage = (Stage) login_form.getScene().getWindow();
            stage.setScene(CarRentalApplication.loadscene("dashboard.fxml",1280,720));
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Dashboard yüklenemedi!");
        }
    }

}