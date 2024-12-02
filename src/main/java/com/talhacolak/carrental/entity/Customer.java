package com.talhacolak.carrental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Data

public class Customer extends BaseEntity {

    @Column(unique = true, nullable = false, length = 6)
    private String licenseNumber;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private Integer phoneNumber;

    @Column(nullable = false)
    private String email;
}
