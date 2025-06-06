package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.config.utils.ArrayList;
import com.espol.contacts.config.utils.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.datasource.ContactsDatasource;

public class ContactsRepositoryImpl implements ContactsRepository, Observer<User> {
    private final BaseDatasource<Contact> datasource;
    private final List<Observer<Contact>> observers;

    private static ContactsRepositoryImpl instance;

    private ContactsRepositoryImpl() {
        this.datasource = ContactsDatasource.getInstance();
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
        observers.addLast(observer);
    }

    @Override
    public void removeObserver(Observer<Contact> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Contact data) {
        observers.forEach(observer -> observer.update(data));
    }

    @Override
    public void update(User data) {
        instance = null; // Reset instance when user changes
    }
}