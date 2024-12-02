package com.talhacolak.carrental.dto;

public enum Role {
    ADMIN("Yönetici", "Admin"),
    USER("Çalışan", "User");

    private final String trName;
    private final String engName;

    Role(String trName, String engName) {
        this.trName = trName;
        this.engName = engName;

    }

    @Override

    public String toString() {
        return trName;
    }
}
