package com.espol.contacts.domain.datasource;

import com.espol.contacts.domain.entity.Contact;

import java.util.List;
import java.util.Optional;

// TODO: Actualizar el tipo de parámetro phone según se defina en Contact
public interface ContactsDatasource {
    List<Contact> getAll();
    Optional<Contact> getById(Long id);
    Optional<Contact> getByPhone(String phone);
    Contact save(Contact contact);
    void delete(Contact contact);
}
