package com.talhacolak.carrental.dto;

public enum Fuel {
    PETROL("Benzinli","Petrol"),
    DIESEL("Dizel","Diesel"),
    HYBRID("Hibrid", "Hybrid"),
    ELECTRIC("Elektrikli", "Electric");

    private final String trName;
    private final String engName;

    Fuel(String trName, String engName) {
        this.trName = trName;
        this.engName = engName;
    }

    @Override
    public String toString() {
        return trName;
    }
}