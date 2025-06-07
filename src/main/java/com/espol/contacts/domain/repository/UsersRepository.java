package com.espol.contacts.domain.repository;

import com.espol.contacts.domain.entity.User;

import java.util.Optional;

public interface UsersRepository {
    Optional<User> authenticate(String username, String password);
    boolean register(User user);
    void logout();
}