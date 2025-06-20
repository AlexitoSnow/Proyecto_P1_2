package com.espol.contacts.domain.datasource;

import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Phone;

import java.util.Optional;

public interface ContactsDatasource {
    List<Contact> getAll();
    List<Contact> getAllByName(String name);
    Optional<Contact> getByPhone(Phone phone);
    Optional<Contact> getById(String id);
    Contact save(Contact entity);
    void delete(Contact entity);
}
