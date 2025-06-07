package com.espol.contacts.infrastructure.datasource;

import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.domain.datasource.UsersDatasource;
import com.espol.contacts.domain.entity.User;

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
        var user = deserializeFile(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(String username) {
        return getFile(username, false).exists();
    }

    @Override
    public User save(User user) {
        return serializeFile(user).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }

    // TODO: Implement deserialization logic properly
    private Optional<User> deserializeFile(String filename) {
        File file = getFile(filename, false);
        return Optional.of(new User(filename, "dummyPassword")); // Dummy password for deserialization
    }

    // TODO: Implement serialization logic properly
    private Optional<User> serializeFile(User user) {
        File file = getFile(user.getUsername(), true);
        return Optional.of(user);
    }

    private File getFile(String filename, boolean createIfNotExists) {
        final File file = new File( Constants.ACCOUNTS_FOLDER + File.separator + filename + ".user");
        if (createIfNotExists && !file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.severe("Error creating user file: " + e.getMessage());
            }
        }
        return file;
    }
}
