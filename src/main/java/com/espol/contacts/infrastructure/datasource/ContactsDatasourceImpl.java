package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.utils.list.ArrayList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.datasource.ContactsDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Phone;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.infrastructure.exception.DuplicatedContactException;

import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsDatasourceImpl implements ContactsDatasource, Observer<User> {

    private List<Contact> contacts;

    private static ContactsDatasourceImpl instance;
    private final static Logger LOGGER = Logger.getLogger(ContactsDatasourceImpl.class.getName());

    private ContactsDatasourceImpl() {
        List<Contact> deserializedList = deserializeFile();
        this.contacts = deserializedList != null ? deserializedList : new ArrayList<>();
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
                serialize(contacts);
                return contact;
            }
        }
        contact.setId(contacts.size() + 1L);
        contacts.addLast(contact);
        LOGGER.info("Guardando contacto ID: " + contact.getId());
        serialize(contacts);
        return contact;
    }

    @Override
    public void delete(Contact contact) {
        contacts.remove(contact);
        serialize(contacts);
    }

    private File getFile() {
        final User user = SessionManager.getInstance().getCurrentUser();
        final File file = new File(Constants.DIRECTORY_FOLDER, user.getUsername() + ".contacts");
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                String path;
                try {
                    path = file.getCanonicalPath();
                } catch (IOException ex) {
                    path = file.getAbsolutePath();
                }
                LOGGER.log(Level.SEVERE, "Error al crear el archivo: " + path, e);
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    // TODO: Review this method and the UsersDatasourceImpl.deserializeFile() method
    // TODO: Merge them into config.utils.Serialization class
    private List<Contact> deserializeFile() {
        File file = getFile();
        if (file.length() == 0) {
            LOGGER.info("El archivo de contactos está vacío o se creó recientemente: " + file.getAbsolutePath());
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            LOGGER.info("Deserializando contactos desde " + file.getCanonicalPath());
            return (List<Contact>)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warning("No se pudo deserializar el archivo de contactos: " + e.getCause());
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Review this method and the UsersDatasourceImpl.serializeFile() method
    // TODO: Merge them into config.utils.Serialization class
    private void serialize(List<Contact> contacts){
        final File file = getFile();
        try {
            LOGGER.info("Serializando contactos en " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getCanonicalPath()))){
            oos.writeObject(contacts);
        }catch(IOException e) {
            LOGGER.severe("Error al serializar los contactos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(User data) {
        if (data == null) {
            LOGGER.info("SessionManager ended, cleaning directory reference.");
            this.contacts = new ArrayList<>();
        } else {
            LOGGER.info("SessionManager started, initializing directory reference.");
            List<Contact> deserializedList = deserializeFile();
            this.contacts = deserializedList != null ? deserializedList : new ArrayList<>();
        }
    }
}
