package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.domain.datasource.UsersDatasource;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.config.utils.Serialization;


import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


public class UsersDatasourceImpl implements UsersDatasource {

    private static UsersDatasourceImpl instance;

    private static final Logger LOGGER = Logger.getLogger(UsersDatasourceImpl.class.getName());

    private UsersDatasourceImpl() {}

    public static UsersDatasourceImpl getInstance() {
        if (instance == null) {
            LOGGER.info("Opening UsersDatasourceImpl");
            instance = new UsersDatasourceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> getAuthenticatedUser(String username, String password) {
        var user = Serialization.deSerializeFile(username,true);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(String username) {
        final User user = SessionManager.getInstance().getCurrentUser();
        return Serialization.getFile(username, true).exists();
    }

    @Override
    public User save(User user) {
        return Serialization.serializeFile(user,user.getUsername(),true).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }



}
