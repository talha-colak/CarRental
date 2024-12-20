module com.talhacolak.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;
    requires static lombok;
    requires annotations;
    requires mysql.connector.j;
    requires com.fasterxml.jackson.annotation;
    requires de.jensd.fx.glyphs.fontawesome;
    requires jbcrypt;
    requires com.google.protobuf;

    opens com.talhacolak.carrental.dto to org.hibernate.orm.core;
    exports com.talhacolak.carrental.dto;

    opens com.talhacolak.carrental.entity to org.hibernate.orm.core;
    exports com.talhacolak.carrental.entity;

    opens com.talhacolak.carrental to javafx.fxml;
    exports com.talhacolak.carrental;

    exports com.talhacolak.carrental.controller;
    opens com.talhacolak.carrental.controller to javafx.fxml;
}