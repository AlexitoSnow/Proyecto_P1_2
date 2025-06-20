package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.EmailType;

import java.io.Serializable;
import java.util.Objects;

public class Email implements Serializable {
    private final EmailType type;
    private final String email;

    public Email(EmailType type, String email) {
        this.type = type;
        this.email = email;
    }

    public EmailType getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email e = (Email) o;
        return Objects.equals(email, e.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    private static final long serialVersionUID = 4728364728364728364L;
}
