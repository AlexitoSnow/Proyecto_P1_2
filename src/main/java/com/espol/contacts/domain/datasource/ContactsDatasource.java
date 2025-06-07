package com.espol.contacts.domain.datasource;

import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.domain.entity.Contact;

public interface ContactsDatasource {
    List<Contact> getAll();
    Contact save(Contact entity);
    void delete(Contact entity);
}
