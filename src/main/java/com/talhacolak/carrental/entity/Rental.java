package com.talhacolak.carrental.entity;

import com.talhacolak.carrental.dto.RentalStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Data
public class Rental extends BaseEntity {

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private Double totalPrice;

    private RentalStatus rentalStatus;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}