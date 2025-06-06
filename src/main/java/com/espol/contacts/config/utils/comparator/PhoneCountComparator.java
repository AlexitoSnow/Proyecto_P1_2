package com.espol.contacts.config.utils.comparator;

import com.espol.contacts.domain.entity.Contact;

import java.util.Comparator;

public class PhoneCountComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact other) {
        final Integer contactPhones = contact.getPhones().size();
        final Integer otherPhones = other.getPhones().size();
        return otherPhones.compareTo(contactPhones);
    }
}
