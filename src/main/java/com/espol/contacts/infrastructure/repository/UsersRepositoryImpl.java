package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.domain.repository.UsersRepository;
import com.espol.contacts.infrastructure.datasource.UsersDatasource;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {

    private UsersDatasource datasource;

    private static UsersRepositoryImpl instance;

    private UsersRepositoryImpl() {
        this.datasource = UsersDatasource.getInstance();
    }

    public static UsersRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UsersRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<User> getAll() {
        return datasource.getAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return datasource.getById(id);
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(Object data) {

    }
}
