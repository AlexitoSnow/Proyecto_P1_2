package com.espol.contacts.config.utils.comparator;

import com.espol.contacts.domain.entity.Contact;

import java.util.Comparator;

public class SocialMediaComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact contact, Contact other) {
        final Integer socialMediasContact = contact.getSocialMedias().size();
        final Integer socialMediasOther = other.getSocialMedias().size();
        return socialMediasOther.compareTo(socialMediasContact);
    }
}
