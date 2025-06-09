package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.utils.Serialization;
import com.espol.contacts.config.utils.list.CircularDoublyLinkedList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Phone;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.infrastructure.exception.DuplicatedContactException;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

public class ContactsDatasourceImpl implements ContactsDatasource, Observer<User> {

    private List<Contact> contacts;

    private static ContactsDatasourceImpl instance;
    private final static Logger LOGGER = Logger.getLogger(ContactsDatasourceImpl.class.getName());
    private static String fileName;

    private ContactsDatasourceImpl() {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.isLoggedIn()) {
            LOGGER.info("SessionManager is not initialized, initializing with empty contacts list.");
            this.contacts = new CircularDoublyLinkedList<>();
            return;
        } else {
            LOGGER.info("SessionManager is initialized, loading contacts from file.");
            fileName = Constants.DIRECTORY_FOLDER + File.separator + sessionManager.getCurrentUser().getUsername() + ".contacts";
            Optional<List<Contact>> deserializedList = Serialization.deserializeFile(fileName);
            if (deserializedList.isPresent()) {
                this.contacts = deserializedList.get();
                LOGGER.info("Contacts loaded successfully.");
            } else {
                this.contacts = new CircularDoublyLinkedList<>();
                LOGGER.warning("No contacts found, initializing with empty list.");
            }
        }
        SessionManager.getInstance().addObserver(this);
    }

    public static ContactsDatasourceImpl getInstance() {
        if (instance == null) {
            instance = new ContactsDatasourceImpl();
            LOGGER.info("Opening ContactsDatasourceImpl");
        }
        return instance;
    }

    @Override
    public List<Contact> getAll() {
        return contacts;
    }

    public Optional<Contact> getByPhone(Phone phone) {
        for (Contact contact : contacts) {
            if (contact.getPhones().contains(phone)) {
                return Optional.of(contact);
            }
        }
        return Optional.empty();
    }

    @Override
    public Contact save(Contact contact) {
        for (Phone phone : contact.getPhones()) {
            if (this.getByPhone(phone).isPresent()) {
                LOGGER.warning("El número de teléfono ya existe en otro contacto.");
                throw new DuplicatedContactException(phone.getNumber());
            }
        }
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            if (c.equals(contact)) {
                contacts.set(i, contact);
                LOGGER.info("Actualizando contacto ID: " + contact.getId());
                Serialization.serializeFile(contacts, fileName);
                return contact;
            }
        }
        contact.setId(contacts.size() + 1L);
        contacts.addLast(contact);
        LOGGER.info("Guardando contacto ID: " + contact.getId());
        Serialization.serializeFile(contacts, fileName);
        return contact;
    }

    @Override
    public void delete(Contact contact) {
        contacts.remove(contact);
        Serialization.serializeFile(contacts,fileName);
    }

    @Override
    public void update(User data) {
        if (data == null) {
            LOGGER.info("SessionManager ended, cleaning directory reference.");
            fileName = null;
            this.contacts = new CircularDoublyLinkedList<>();
        } else {
            LOGGER.info("SessionManager started, initializing directory reference.");
            fileName = Constants.DIRECTORY_FOLDER + File.separator + data.getUsername() + ".contacts";
            Optional<List<Contact>> deserializedList = Serialization.deserializeFile(fileName);
            if (deserializedList.isPresent()) {
                this.contacts = deserializedList.get();
                LOGGER.info("Contacts loaded successfully.");
            } else {
                this.contacts = new CircularDoublyLinkedList<>();
                LOGGER.warning("No contacts found, initializing with empty list.");
            }
        }
    }
}
