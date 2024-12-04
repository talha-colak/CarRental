package com.talhacolak.carrental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "inspection")
@Data

public class Inspection extends BaseEntity {

    @Column
    private LocalDate inspectionDate;

    private Boolean registration;

    private Boolean firstAidKit;

    private Boolean fireExtinguisher;

    private Boolean babySeat;

    private Boolean floorMat;

    private Boolean aerial;

    private Boolean spareTyre;

    private Boolean toolSet;
/*
    @Column(nullable = false)
    private Boolean lighter;
    @Column(nullable = false)
    private String tyres;
*/

    private Integer kilometer;

    private Integer fuelStatus;

    private String description;
}