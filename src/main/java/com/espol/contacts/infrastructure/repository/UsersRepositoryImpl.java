package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.domain.repository.UsersRepository;
import com.espol.contacts.infrastructure.datasource.UsersDatasourceImpl;

import java.util.Optional;
import java.util.logging.Logger;

public class UsersRepositoryImpl implements UsersRepository {

    private final UsersDatasourceImpl datasource;

    private static UsersRepositoryImpl instance;
    private static final Logger LOGGER = Logger.getLogger(UsersRepositoryImpl.class.getName());

    private UsersRepositoryImpl() {
        datasource = UsersDatasourceImpl.getInstance();
    }

    public static UsersRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UsersRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> authenticate(String username, String password) {
        if (datasource.exists(username)) {
            return datasource.getAuthenticatedUser(username, password);
        }
        return Optional.empty();
    }

    @Override
    public boolean register(User user) {
        if (datasource.exists(user.getUsername())) {
            return false;
        }
        datasource.save(user);
        return true;
    }

    @Override
    public void logout() {
        LOGGER.info("User logged out: " + SessionManager.getInstance().getCurrentUser().getUsername());
        instance = null;
        SessionManager.getInstance().clearSession();
    }
}
