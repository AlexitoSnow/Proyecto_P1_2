package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.domain.datasource.UsersDatasource;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.config.utils.Serialization;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;


public class UsersDatasourceImpl implements UsersDatasource {

    private static UsersDatasourceImpl instance;
    private String userPath;

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
        userPath = Constants.ACCOUNTS_FOLDER + File.separator + username + ".user";
        Optional<User> user = Serialization.deserializeFile(userPath);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(String username) {
        userPath = Constants.ACCOUNTS_FOLDER + File.separator + username + ".user";
        return Serialization.deserializeFile(userPath).isPresent();
    }

    @Override
    public User save(User user) {
        userPath = Constants.ACCOUNTS_FOLDER + File.separator + user.getUsername() + ".user";
        Serialization.serializeFile(user, userPath);
        LOGGER.info("User saved: " + user.getUsername() + " at " + userPath);
        return user;
    }



}
