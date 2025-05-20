package com.espol.contacts.domain.datasource;

import com.espol.contacts.domain.entity.Contact;

import java.util.List;

public interface ContactsDatasource {
    List<Contact> getAll();
    Contact getById(long id);
    Contact save(Contact contact);
    void delete(Contact contact);
}
