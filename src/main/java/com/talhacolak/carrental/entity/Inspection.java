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

    @Column(nullable = false)
    private LocalDate inspectionDate;

    @Column(nullable = false)
    private Boolean registration;

    @Column(nullable = false)
    private Boolean firstAidKit;

    @Column(nullable = false)
    private Boolean fireExtinguisher;

    @Column(nullable = false)
    private Boolean babySeat;

    @Column(nullable = false)
    private Boolean floorMat;

    @Column(nullable = false)
    private Boolean aerial;

    @Column(nullable = false)
    private Boolean spareTyre;

    @Column(nullable = false)
    private Boolean toolSet;
/*
    @Column(nullable = false)
    private Boolean lighter;
    @Column(nullable = false)
    private String tyres;
*/

    @Column(nullable = false)
    private Integer kilometer;

    @Column(nullable = false)
    private Integer fuelStatus;

    @Column(nullable = false)
    private String description;
}