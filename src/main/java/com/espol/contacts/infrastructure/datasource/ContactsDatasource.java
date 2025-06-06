package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.utils.ArrayList;
import com.espol.contacts.config.utils.List;
import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Phone;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.infrastructure.exception.DuplicatedContactException;
import com.espol.contacts.infrastructure.repository.UsersRepositoryImpl;

import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsDatasource implements BaseDatasource<Contact> {

    private final User user;
    private final List<Contact> contacts;

    private static ContactsDatasource instance;
    private final static Logger LOGGER = Logger.getLogger(ContactsDatasource.class.getName());

    private ContactsDatasource() {
        this.user = UsersRepositoryImpl.getLoggedUser();
        List<Contact> deserializedList = deserializeFile();
        this.contacts = deserializedList != null ? deserializedList : new ArrayList<>();
    }

    public static ContactsDatasource getInstance() {
        if (instance == null) {
            instance = new ContactsDatasource();
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
}
