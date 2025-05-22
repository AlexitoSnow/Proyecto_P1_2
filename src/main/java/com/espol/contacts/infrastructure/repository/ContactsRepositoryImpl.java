package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.datasource.DevContactsDatasourceImpl;

import java.util.List;
import java.util.Optional;

public class ContactsRepositoryImpl implements ContactsRepository {
    private final ContactsDatasource datasource;

    public ContactsRepositoryImpl() {
        this.datasource = new DevContactsDatasourceImpl();
    }

    @Override
    public List<Contact> getAll() {
        return datasource.getAll();
    }

    @Override
    public Optional<Contact> getByPhone(long phone) {
        return datasource.getByPhone(phone);
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