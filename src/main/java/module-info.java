module com.talhacolak.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.talhacolak.carrental to javafx.fxml;
    exports com.talhacolak.carrental;
}