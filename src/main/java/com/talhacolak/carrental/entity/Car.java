package com.talhacolak.carrental.entity;

import com.talhacolak.carrental.dto.Fuel;
import com.talhacolak.carrental.dto.Gear;
import com.talhacolak.carrental.dto.Category;
import com.talhacolak.carrental.dto.CarStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //getter setter metodları için
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity {

    private String licensePlate;
    private String brand;
    private String model;
    private int price;
    private int year;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @Enumerated(EnumType.STRING)
    private Gear gear;

    @Enumerated(EnumType.STRING)
    private CarStatus status;
}
//fuel ENUM('Benzinli', 'Dizel', 'Hibrit' DEFAULT 'Beklemede') , gear ENUM('Manuel', 'Otomatik' DEFAULT 'Beklemede'), year INT, plate VARCHAR(50), price INT, status ENUM('Müsait', 'Kirada', 'Bakımda') DEFAULT 'Beklemede')";
