package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.config.utils.list.ArrayList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Phone;
import com.espol.contacts.domain.entity.RelatedContact;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.datasource.ContactsDatasourceImpl;
import com.espol.contacts.infrastructure.exception.DuplicatedPhoneException;

import java.util.logging.Logger;

public class ContactsRepositoryImpl implements ContactsRepository {
    private final ContactsDatasource datasource;
    private final List<Observer<Contact>> observers;

    private static ContactsRepositoryImpl instance;

    private static final Logger LOGGER = Logger.getLogger(ContactsRepositoryImpl.class.getName());

    private ContactsRepositoryImpl() {
        this.datasource = ContactsDatasourceImpl.getInstance();
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
    public List<Contact> getAllByName(String name) {
        return datasource.getAllByName(name);
    }

    @Override
    public Contact save(Contact contact) throws DuplicatedPhoneException {
        final var existingContact = datasource.getById(contact.getId());

        if (existingContact.isEmpty()) {
            for (Phone phone : contact.getPhones()) {
                if (datasource.getByPhone(phone).isPresent()) {
                    LOGGER.severe("El número de teléfono ya existe en otro contacto.");
                    throw new DuplicatedPhoneException(phone.getNumber());
                }
            }
        }

        if (existingContact.isPresent()){
            for (Contact c : datasource.getAll()) {
                for (RelatedContact relatedContact : c.getRelatedContacts()) {
                    if (relatedContact.getContact().equals(existingContact.get())) {
                        relatedContact.setContact(contact);
                    }
                }
            }
        }

        final Contact saved = datasource.save(contact);
        notifyObservers(contact);
        return saved;
    }

    @Override
    public void delete(Contact contact) {
        final var existingContact = datasource.getById(contact.getId());
        for (Contact c : datasource.getAll()) {
            c.getRelatedContacts().removeIf(relatedContact -> relatedContact.getContact().equals(existingContact.get()));
        }
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
}