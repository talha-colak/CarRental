package com.talhacolak.carrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspection")
@Data

public class Inspection extends BaseEntity {

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime inspectionDate;

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

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}