package com.espol.contacts.domain.repository;

import com.espol.contacts.config.utils.observer.Observable;
import com.espol.contacts.domain.entity.User;

import java.util.List;
import java.util.Optional;

// TODO: Actualizar el tipo de parámetro phone según se defina en Contact
public interface UsersRepository extends Observable {
    List<User> getAll();
    Optional<User> getById(Long id);
    User save(User user);
    void delete(User user);
}