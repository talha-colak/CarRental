package com.talhacolak.carrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.talhacolak.carrental.dto.RentalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Data
public class Rental extends BaseEntity {

    @JsonFormat(pattern = "dd-mm-yyyy HH:mm:ss")
    private LocalDateTime rentalDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime returnDate;

    private Double totalPrice;

    private RentalStatus rentalStatus;

    @ManyToOne
    @JoinColumn(name = "beforeinspection_id")
    private Inspection beforeInspection;

    @ManyToOne
    @JoinColumn(name = "afterinspection_id")
    private Inspection afterInspection;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}