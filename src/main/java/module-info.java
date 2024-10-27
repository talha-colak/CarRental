module com.talhacolak.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.talhacolak.carrental to javafx.fxml;
    exports com.talhacolak.carrental;
    exports com.talhacolak.carrental.controller;
    opens com.talhacolak.carrental.controller to javafx.fxml;
}