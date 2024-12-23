package com.talhacolak.carrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private Integer kilometer;

    private Integer fuelStatus;

    private String description;

}