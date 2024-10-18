module com.talhacolak.carrental {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.talhacolak.carrental to javafx.fxml;
    exports com.talhacolak.carrental;
}