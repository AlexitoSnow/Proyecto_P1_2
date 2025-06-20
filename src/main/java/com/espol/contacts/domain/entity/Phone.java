package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.PhoneType;

import java.io.Serializable;
import java.util.Objects;

public class Phone implements Serializable {
    private final PhoneType type;
    private final String number;

    public Phone(PhoneType type, String number) {
        this.type = type;
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    private static final long serialVersionUID = 1948572039485720394L;
}
