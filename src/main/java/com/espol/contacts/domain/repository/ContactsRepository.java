package com.espol.contacts.domain.repository;

import com.espol.contacts.config.utils.List;
import com.espol.contacts.config.utils.observer.Observable;
import com.espol.contacts.domain.entity.Contact;

public interface ContactsRepository extends Observable<Contact> {
    List<Contact> getAll();
    Contact save(Contact contact);
    void delete(Contact contact);
}