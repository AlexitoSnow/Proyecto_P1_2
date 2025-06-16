package com.espol.contacts.domain.entity.enums;

public enum DateType {
    BIRTHDAY("CUMPLEAÑOS"),
    ANNIVERSARY("ANIVERSARIO"),
    OTHER("OTRO");

    private final String name;

    DateType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
