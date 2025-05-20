package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;

import java.util.List;

public class ContactsDatasourceImpl implements ContactsDatasource {
    @Override
    public List<Contact> getAll() {
        return List.of();
    }

    @Override
    public Contact getById(long id) {
        return null;
    }

    @Override
    public Contact save(Contact contact) {
        return null;
    }

    @Override
    public void delete(Contact contact) {

    }
}
