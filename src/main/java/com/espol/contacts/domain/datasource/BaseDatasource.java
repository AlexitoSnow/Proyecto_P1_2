package com.espol.contacts.domain.datasource;

import com.espol.contacts.config.utils.List;

public interface BaseDatasource<T> {
    List<T> getAll();
    T save(T entity);
    void delete(T entity);
}
