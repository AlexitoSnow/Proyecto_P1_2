package com.espol.contacts.domain.entity.enums;

public enum PhoneType {
    MAIN("PRINCIPAL"),
    PHONE("MÓVIL"),
    HOME("CASA"),
    WORK("TRABAJO"),
    OTHER("OTRO");

    private final String value;

    PhoneType(String value) {
        this.value = value;
    }
}
