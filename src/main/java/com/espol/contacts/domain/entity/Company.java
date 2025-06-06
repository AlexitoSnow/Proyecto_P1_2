package com.espol.contacts.domain.entity;

import java.io.Serializable;

public class Company extends Contact implements Serializable {

    private Company() {
        super();
    }

    public static Company build() {
        return new Company();
    }

}
