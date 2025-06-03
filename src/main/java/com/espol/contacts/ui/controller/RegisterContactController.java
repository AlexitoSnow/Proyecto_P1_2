package com.espol.contacts.ui.controller;

import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.ui.fragments.contactForm.ContactForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterContactController implements Initializable, DataInitializable<ContactType> {
    @FXML
    public HBox mainHBox;

    private ContactType contactType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ContactForm contactForm = new ContactForm(contactType);
        mainHBox.getChildren().addAll(contactForm);
    }

    @Override
    public void initData(ContactType data) {
        contactType = data;
    }
}
