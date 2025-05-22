package com.espol.contacts.domain.entity;

public class Company extends Contact{

    private Company() {
        super();
    }

    public static Company build() {
        return new Company();
    }

}
