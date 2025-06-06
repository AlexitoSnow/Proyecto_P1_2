package com.espol.contacts.config.utils.comparator;

import com.espol.contacts.domain.entity.Contact;

import java.util.Comparator;

public class EmailCountComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact other) {
        final Integer emailsContact = contact.getEmails().size();
        final Integer emailsOther = other.getEmails().size();
        return emailsOther.compareTo(emailsContact);
    }
}
