package com.espol.contacts.config.utils.observer;

public interface Observable<T> {
    public void addObserver(Observer<T> observer);
    public void removeObserver(Observer<T> observer);
    public void notifyObservers(T data);
}
