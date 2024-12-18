package com.talhacolak.carrental.controller;

import com.talhacolak.carrental.dto.Role;
import com.talhacolak.carrental.entity.User;
import com.talhacolak.carrental.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class UserAddController {

    @FXML
    private TextField usernameField, passwordField, nameField, surnameField;

    @FXML
    private Button userAddButton;

    @FXML
    private RadioButton ADMIN, USER;

    @FXML
    private ToggleGroup Roles;

    @FXML
    private GridPane userAddForm;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {

        ADMIN.setText(Role.ADMIN.toString());
        USER.setText(Role.USER.toString());

    }

    @FXML
    private void userAdd() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        Toggle toggle = Roles.getSelectedToggle();
        RadioButton selectedRole = (RadioButton) toggle;
        System.out.println(toggle);

        User user = new User();
        user.setFirstName(name);
        user.setLastName(surname);
        user.setUserName(username);
        user.setRole(Role.valueOf(selectedRole.getId()));
        user.setPassword(UserService.hashPassword(password));

        userService.addUser(user);

    }
}