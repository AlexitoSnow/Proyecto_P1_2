package com.espol.contacts.domain.repository;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;

import java.util.List;
import java.util.Optional;

// TODO: Actualizar el tipo de parámetro phone según se defina en Contact
public interface ContactsRepository {
    List<Contact> getAll();
    List<Contact> getFavorites();
    List<Contact> getByType(ContactType type);
    Optional<Contact> getById(Long id);
    Optional<Contact> getByPhone(String phone);
    Contact save(Contact contact);
    void delete(Contact contact);
}