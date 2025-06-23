package com.espol.contacts.domain.entity.enums;

public enum DateType {
    BIRTHDAY("Cumpleaños"),
    ANNIVERSARY("Aniversario"),
    OTHER("Otro");

    private final String name;

    DateType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
