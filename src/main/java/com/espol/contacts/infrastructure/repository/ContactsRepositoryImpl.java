package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.datasource.ContactsDatasourceImpl;

import java.util.List;

public class ContactsRepositoryImpl implements ContactsRepository {
    private final ContactsDatasource datasource;

    public ContactsRepositoryImpl() {
        this.datasource = new ContactsDatasourceImpl();
    }

    @Override
    public List<Contact> getAll() {
        return datasource.getAll();
    }

    @Override
    public Contact getById(long id) {
        return datasource.getById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return datasource.save(contact);
    }

    @Override
    public void delete(Contact contact) {
        datasource.delete(contact);
    }
}
