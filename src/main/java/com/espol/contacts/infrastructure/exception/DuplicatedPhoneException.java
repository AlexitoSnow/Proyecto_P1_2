package com.espol.contacts.infrastructure.exception;

public class DuplicatedPhoneException extends IllegalArgumentException {
    public DuplicatedPhoneException(String number) {
        super("Ya existe un contacto con el celular proporcionado: " + number);
    }
}
