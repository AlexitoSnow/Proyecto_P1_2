package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.datasource.DevContactsDatasourceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactsRepositoryImpl implements ContactsRepository {
    private final ContactsDatasource datasource;

    public ContactsRepositoryImpl() {
        this.datasource = DevContactsDatasourceImpl.getInstance();
    }

    @Override
    public List<Contact> getAll() {
        return datasource.getAll();
    }

    @Override
    public List<Contact> getFavorites() {
        return getAll().stream().filter(Contact::isFavorite).collect(Collectors.toList());
    }

    @Override
    public List<Contact> getByType(ContactType type) {
        return getAll().stream().filter(contact -> contact.getContactType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Optional<Contact> getById(Long id) {
        return datasource.getById(id);
    }

    @Override
    public Optional<Contact> getByPhone(String phone) {
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