package com.espol.contacts.ui.controller;

import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.ui.fragments.contactForm.ContactForm;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.config.router.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterContactController implements Initializable, DataInitializable<ContactType> {
    @FXML
    private HBox container;

    private ContactForm formControl;

    private ContactType contactType;
    private final ContactsRepository repository;

    public RegisterContactController() {
        repository = new ContactsRepositoryImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formControl = new ContactForm(contactType);
        VBox mediaControl = new VBox();
        Separator separator = new Separator(Orientation.VERTICAL);
        container.getChildren().addAll(formControl, separator, mediaControl);
        HBox.setHgrow(formControl, Priority.ALWAYS);
        HBox.setHgrow(mediaControl, Priority.ALWAYS);
    }

    @Override
    public void initData(ContactType data) {
        contactType = data;
    }

    @FXML
    void goBack(ActionEvent event) {
        AppRouter.setRoot(Routes.HOME);
    }

    @FXML
    void saveContact(ActionEvent event) {
        var contact = formControl.getContact();
        if (contact != null) {
            repository.save(contact);
            Notifications.create()
                    .title("Info: Creación de contacto")
                    .text("Contacto creado")
                    .graphic(new FontIcon(FontAwesomeSolid.CHECK_CIRCLE))
                    .show();
            goBack(null);
        }
    }
}
