package com.talhacolak.carrental.dto;

public enum CarStatus {
RESERVED("Rezerve","Reserved"),
AVAILABLE("Müsait","On Hand"),
RENTED("Kirada","Rented"),
SERVICE("Serviste","Out Of Service"),
UNDEFINED("Tanımsız","Undefined")
;

private final String trName;
private final String engName;

CarStatus(String trName, String engName) {
    this.trName = trName;
    this.engName = engName;
}

public String toString() {return trName;}
}