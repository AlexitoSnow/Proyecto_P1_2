package com.espol.contacts.domain.repository;

import com.espol.contacts.domain.entity.Contact;

import java.util.List;
import java.util.Optional;

// TODO: Actualizar el tipo de parámetro phone según se defina en Contact
public interface ContactsRepository {
    List<Contact> getAll();
    Optional<Contact> getByPhone(long phone);
    Contact save(Contact contact);
    void delete(Contact contact);
}