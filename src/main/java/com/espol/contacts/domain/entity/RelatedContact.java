package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.Relationship;

import java.io.Serializable;

public class RelatedContact implements Serializable {
    private Relationship relationship;
    private Contact contact;

    public RelatedContact(Relationship relationship, Contact contact) {
        this.relationship = relationship;
        this.contact = contact;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
