package com.espol.contacts.config.utils.comparator;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;

import java.util.Comparator;

public class ContactTypeComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact other) {
        return contact.getContactType().compareTo(other.getContactType());
    }
}
