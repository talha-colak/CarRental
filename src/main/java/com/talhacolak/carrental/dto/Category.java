package com.talhacolak.carrental.dto;

public enum Category {
    SEDAN("Sedan","Sedan"),
    SUV("SUV","SUV"),
    HATCHBACK("Hatchback","Hatchback"),
    UNDEFINED("Tanımsız","Undefined");

    private final String trName;
    private final String engName;

    Category(String trName, String engName) {
        this.trName = trName;
        this.engName = engName;
    }

    @Override
    public String toString() {
        return trName;
    }

}