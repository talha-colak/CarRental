package com.talhacolak.carrental.dto;

public enum Gear {
    Manual("Manuel", "Manual"),
    Automatic("Otomatik", "Automatic"),
    UNDEFINED("Tanımsız","Undefined");

    private final String trName;
    private final String engName;

    Gear(String trName, String engName) {
        this.trName = trName;
        this.engName = engName;
    }

    @Override
    public String toString(){
        return trName;
    }
}
