package com.espol.contacts.domain.entity.enums;

public enum PhoneType {
    MAIN("Principal"),
    PHONE("Móvil"),
    HOME("Casa"),
    WORK("Trabajo"),
    OTHER("Otro");

    private final String value;

    PhoneType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
