package com.talhacolak.carrental.entity;

import com.talhacolak.carrental.dto.Fuel;
import com.talhacolak.carrental.dto.Gear;
import com.talhacolak.carrental.dto.Category;
import com.talhacolak.carrental.dto.CarStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data //getter setter metodları için
@ToString(exclude = "inspectionList")
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity {

    @Column(unique = true, nullable = false, length = 8)
    private String licensePlate;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 50)
    private String model;

    private String imageUrl;

    private int year;

    private int price;

    @Enumerated(EnumType.STRING)
    private Category category = Category.UNDEFINED;

    @Enumerated(EnumType.STRING)
    private Fuel fuel = Fuel.UNDEFINED;

    @Enumerated(EnumType.STRING)
    private Gear gear = Gear.UNDEFINED;

    @Enumerated(EnumType.STRING)
    private CarStatus status = CarStatus.UNDEFINED;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Inspection> inspectionList = new ArrayList<>();

}