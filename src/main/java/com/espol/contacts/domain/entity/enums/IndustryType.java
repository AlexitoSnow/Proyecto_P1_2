package com.espol.contacts.domain.entity.enums;

public enum IndustryType {
    AGRICULTURE("AGRICULTURA"),
    AUTOMOTIVE("AUTOMOTRIZ"),
    CONSTRUCTION("CONSTRUCCIÓN"),
    EDUCATION("EDUCACIÓN"),
    FINANCE("FINANZAS"),
    HEALTHCARE("SALUD"),
    HOSPITALITY("HOSPITALARIA"),
    SOFTWARE("SOFTWARE"),
    OTHER("OTRA");

    private final String name;

    IndustryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
