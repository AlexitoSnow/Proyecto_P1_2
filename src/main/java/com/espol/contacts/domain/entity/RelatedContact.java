package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.Relationship;

public class RelatedContact {
    private Relationship relationship;
    private String phoneNumber;

    public RelatedContact(Relationship relationship, String phoneNumber) {
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
