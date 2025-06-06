package com.espol.contacts.infrastructure.exception;

public class DuplicatedContactException extends IllegalArgumentException {
    public DuplicatedContactException(String number) {
        super("Ya existe un contacto con el celular proporcionado: " + number);
    }
}
