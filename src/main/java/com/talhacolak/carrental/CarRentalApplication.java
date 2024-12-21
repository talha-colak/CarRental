package com.talhacolak.carrental;

import com.talhacolak.carrental.dto.Role;
import com.talhacolak.carrental.entity.User;
import com.talhacolak.carrental.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class CarRentalApplication extends Application {

    UserService userService;

    //yeni
    public static Scene loadscene(String fxmlpath, int width, int height, Locale locale) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarRentalApplication.class.getResource(fxmlpath));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("languages.crs_localization", locale);
        fxmlLoader.setResources(resourceBundle);
        Parent root = fxmlLoader.load();
        return new Scene(root, width, height);
    }

    @Override
    public void start(Stage stage) throws IOException {

        userService = new UserService();
        User user = userService.getUserByUsername("admin");
        if (Objects.isNull(user)) {
            User adminUser = new User("admin", UserService.hashPassword("alyattes"), Role.ADMIN);
            userService.addUser(adminUser);
        }

        Locale defaultLocale = new Locale("tr", "TR");
        Locale.setDefault(defaultLocale);

        stage.setScene(loadscene("login-view.fxml", 600, 400, defaultLocale));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Login Screen");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}