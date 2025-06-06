package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.User;

import java.util.List;
import java.util.Optional;

public class UsersDatasource implements BaseDatasource<User> {

    private static UsersDatasource instance;

    private UsersDatasource() {
        // Constructor privado para evitar instanciación externa
    }

    public static UsersDatasource getInstance() {
        if (instance == null) {
            instance = new UsersDatasource();
        }
        return instance;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Contact save(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }
}
