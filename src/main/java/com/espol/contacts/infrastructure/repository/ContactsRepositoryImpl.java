package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.datasource.DevContactsDatasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactsRepositoryImpl implements ContactsRepository {
    private final BaseDatasource<Contact> datasource;
    private final List<Observer<Contact>> observers;

    private static ContactsRepositoryImpl instance;

    private ContactsRepositoryImpl() {
        this.datasource = DevContactsDatasource.getInstance();
        this.observers = new ArrayList<>();
    }

    public static ContactsRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ContactsRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Contact> getAll() {
        return datasource.getAll();
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
        final Contact saved = datasource.save(contact);
        notifyObservers(contact);
        return saved;
    }

    @Override
    public void delete(Contact contact) {
        datasource.delete(contact);
        notifyObservers(null);
    }

    @Override
    public void addObserver(Observer<Contact> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Contact> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Contact data) {
        observers.forEach(observer -> observer.update(data));
    }
}