package com.espol.contacts.domain.entity.enums;

public enum IndustryType {
    AGRICULTURE("Agricultura"),
    AUTOMOTIVE("Automotriz"),
    CONSTRUCTION("Construcción"),
    EDUCATION("Educación"),
    FINANCE("Finanzas"),
    HEALTHCARE("Salud"),
    HOSPITALITY("Hospitalaria"),
    SOFTWARE("Software"),
    OTHER("Otra");

    private final String name;

    IndustryType(String name) {
        this.name = name;
    }
}
