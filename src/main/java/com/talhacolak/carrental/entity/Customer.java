package com.talhacolak.carrental.entity;

import jakarta.persistence.Entity;

@Entity
public class Customer extends BaseEntity {

    private String driverLicenseNumber;
    private String firstName;
    private String lastName;
    private String birthday;
    private Integer phoneNumber;
    private String address;
    private String email;

}
