package com.espol.contacts.config.utils.comparator;

import com.espol.contacts.domain.entity.Contact;

import java.util.Comparator;

public class NameComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact other) {
        if (contact.getName() == null) return -1;
        if (other.getName() == null) return 1;
        return contact.getName().compareTo(other.getName());
    }
}
