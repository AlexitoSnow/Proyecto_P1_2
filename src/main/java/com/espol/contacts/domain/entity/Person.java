package com.espol.contacts.domain.entity;

public class Person extends Contact {
    private String middleName;
    private String lastName;

    private Person() {
        super();
    }

    public static Person build() {
        return new Person();
    }

    public Person middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }
}
