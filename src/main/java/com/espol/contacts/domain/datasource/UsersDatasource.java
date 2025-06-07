package com.espol.contacts.domain.datasource;

import com.espol.contacts.domain.entity.User;

import java.util.Optional;

public interface UsersDatasource {

    Optional<User> getAuthenticatedUser(String username, String password);
    boolean exists(String username);
    User save(User user);
}
