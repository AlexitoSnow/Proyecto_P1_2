package com.espol.contacts.domain.datasource;

import com.espol.contacts.domain.entity.Contact;

import java.util.List;
import java.util.Optional;

// TODO: Actualizar el tipo de parámetro phone según se defina en Contact
public interface BaseDatasource<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    Optional<T> getByPhone(String phone);
    Contact save(T entity);
    void delete(T entity);
}
