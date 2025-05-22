package com.espol.contacts.domain.entity.enums;

public enum DateType {
    BIRTHDAY("CUMPLEAÑOS"),
    ANNIVERSARY("ANIVERSARIO"),
    OTHER("OTRO");

    private final String value;

    DateType(String value) {
        this.value = value;
    }
}
