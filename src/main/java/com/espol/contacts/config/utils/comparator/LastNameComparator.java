package com.espol.contacts.config.utils.comparator;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;

import java.util.Comparator;

public class LastNameComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact other) {
        if (contact instanceof Person && other instanceof Person) {
            Person person = (Person) contact;
            Person otherPerson = (Person) other;
            if (person.getLastName() == null) return -1;
            if (otherPerson.getLastName() == null) return 1;
            return person.getLastName().compareTo(otherPerson.getLastName());
        } else if (contact instanceof Person) {
            return 1;
        } else if (other instanceof Person) {
            return -1;
        }
        return 0;
    }
}
