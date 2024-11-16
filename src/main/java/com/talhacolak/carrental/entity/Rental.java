package com.talhacolak.carrental.entity;

import com.talhacolak.carrental.dto.RentalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Rental extends BaseEntity {

    private LocalDateTime rentalDate ;
    private LocalDateTime returnDate ;
    private Double totalPrice;
    private RentalStatus rentalStatus;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

}
