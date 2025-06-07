package com.espol.contacts.config;

import com.espol.contacts.config.utils.list.ArrayList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observable;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.entity.User;

public class SessionManager implements Observable<User> {
    private static SessionManager instance;
    private User currentUser;
    private final List<Observer<User>> observers;

    private SessionManager() {
        this.observers = new ArrayList<>();
        this.currentUser = null;
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        notifyObservers(user);
    }

    public void clearSession() {
        this.currentUser = null;
        notifyObservers(null);
    }

    public boolean isLoggedIn() {
        return currentUser != null;
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