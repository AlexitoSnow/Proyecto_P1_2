package com.espol.contacts.domain.repository;

import com.espol.contacts.config.utils.observer.Observable;
import com.espol.contacts.domain.entity.User;

import java.util.Optional;

public interface UsersRepository extends Observable<User> {
    boolean login(String username, String password);
    boolean register(User user);
    void logout();
    Optional<User> getByUsername(String username);
    Optional<User> getByEmail(String email);
}