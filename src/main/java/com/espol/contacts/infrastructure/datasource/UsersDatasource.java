package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.utils.ArrayList;
import com.espol.contacts.config.utils.List;
import com.espol.contacts.domain.datasource.BaseDatasource;
import com.espol.contacts.domain.entity.User;

import java.util.logging.Logger;

public class UsersDatasource implements BaseDatasource<User> {

    private static UsersDatasource instance;
    private final List<User> users;

    private static final Logger LOGGER = Logger.getLogger(UsersDatasource.class.getName());

    private UsersDatasource() {
        this.users = new ArrayList<>();
    }

    public static UsersDatasource getInstance() {
        if (instance == null) {
            LOGGER.info("Opening UsersDatasource");
            instance = new UsersDatasource();
        }
        return instance;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User save(User entity) {
        return users.addLast(entity) ? entity : null;
    }

    @Override
    public void delete(User entity) {
        users.remove(entity);
    }

}
