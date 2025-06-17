package com.espol.contacts.domain.repository;

import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observable;
import com.espol.contacts.domain.entity.Contact;

public interface ContactsRepository extends Observable<Contact> {
    List<Contact> getAll();
    List<Contact> getAllByName(String name);
    Contact save(Contact contact);
    void delete(Contact contact);
}