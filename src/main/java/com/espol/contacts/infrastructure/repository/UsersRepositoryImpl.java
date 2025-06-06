package com.espol.contacts.infrastructure.repository;

import com.espol.contacts.config.utils.ArrayList;
import com.espol.contacts.config.utils.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.domain.repository.UsersRepository;
import com.espol.contacts.infrastructure.datasource.UsersDatasource;

import java.util.Optional;
import java.util.logging.Logger;

public class UsersRepositoryImpl implements UsersRepository {

    private final UsersDatasource datasource;
    private final List<Observer<User>> observers;
    private static User loggedUser;

    private static UsersRepositoryImpl instance;
    private static final Logger LOGGER = Logger.getLogger(UsersRepositoryImpl.class.getName());

    private UsersRepositoryImpl() {
        datasource = UsersDatasource.getInstance();
        observers = new ArrayList<>();
    }

    public static UsersRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new UsersRepositoryImpl();
        }
        return instance;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    @Override
    public boolean login(String username, String password) {
        List<User> users = datasource.getAll();
        for (User u : users) {
            if (u.equals(new User(username, password))) {
                loggedUser = u;
                LOGGER.info("Logged in user: " + username);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        if (getByUsername(user.getUsername()).isEmpty() && getByEmail(user.getEmail()).isEmpty()) {
            datasource.save(user);
            loggedUser = user;
            LOGGER.info("Registered user: " + user.getUsername());
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        LOGGER.info("User logged out: " + loggedUser.getUsername());
        instance = null;
        loggedUser = null;
        notifyObservers(null);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        List<User> users = datasource.getAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        List<User> users = datasource.getAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void addObserver(Observer<User> observer) {
        observers.addLast(observer);
    }

    @Override
    public void removeObserver(Observer<User> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(User data) {
        observers.forEach(observer -> observer.update(data));
    }
}
