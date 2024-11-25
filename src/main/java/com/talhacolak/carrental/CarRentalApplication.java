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
import java.util.Objects;

public class CarRentalApplication extends Application {

    UserService userService;
    //yeni
    public static Scene loadscene(String fxmlpath, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarRentalApplication.class.getResource(fxmlpath));
        Parent root = fxmlLoader.load();
        return new Scene(root, width, height);
    }

    @Override
    public void start(Stage stage) throws IOException {

        userService = new UserService();
        User user = userService.getUserByUsername("admin");
        if (Objects.isNull(user)) {
            User adminUser = new User("admin", "123456", Role.ADMIN);
            userService.addUser(adminUser);
        }

        stage.setScene(loadscene("login-view.fxml", 600, 400));
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

//eski
/*
    //
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarRentalApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
*/