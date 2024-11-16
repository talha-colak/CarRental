package com.talhacolak.carrental.dto;

public enum RentalStatus {
RESERVED("Rezerve","Reserved"),
ONGOING("Aktif","Ongoing"),
OVERDUE("Gecikti","Overdue"),
FINISHED("Tamamlandı","Finished"),
CANCELLED("İptal","Cancelled"),
UNDEFINED("Tanımsız","Undefined")
;
private final String trName;
private final String engName;

RentalStatus(String trName, String engName){
    this.trName = trName;
    this.engName = engName;

}
}
