package com.talhacolak.carrental.dto;

public enum Category {
    SEDAN("Sedan"),
    SUV("SUV"),
    HATCHBACK("Hatchback");

    private final String trName;

    Category(String trName) {
        this.trName = trName;
    }

    @Override
    public String toString() {
        return trName;
    }

}