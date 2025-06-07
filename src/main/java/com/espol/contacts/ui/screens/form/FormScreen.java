package com.espol.contacts.ui.screens.form;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.ui.screens.form.fragments.ContactForm;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.config.router.*;
import com.espol.contacts.ui.fragments.ExtraInfoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class FormScreen implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private HBox container;
    @FXML
    private ScrollPane leftPane;
    @FXML
    private ScrollPane rightPane;
    @FXML
    private Label titleLabel;

    private ContactForm formControl;
    private ExtraInfoView extraInfoView;

    private ContactType contactType;
    private Contact contact;

    private final ContactsRepository repository;

    public FormScreen() {
        repository = ContactsRepositoryImpl.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formControl = contactType == null ? new ContactForm(contact) : new ContactForm(contactType);
        titleLabel.setText(contactType == null ? "Editar Contacto" : "Crear contacto " + contactType.name());
        extraInfoView = new ExtraInfoView(contact, true);
        formControl.setMaxWidth(Double.MAX_VALUE);
        leftPane.setContent(formControl);
        rightPane.setContent(extraInfoView);
    }

    @FXML
    void goBack(ActionEvent event) {
        AppRouter.setRoot(Routes.HOME);
    }

    @FXML
    void saveContact(ActionEvent event) {
        this.contact = formControl.getContact();
        if (contact != null) {
            contact.setProfilePicture(extraInfoView.getProfilePicture());
            contact.setRelatedContacts(extraInfoView.getRelatedContacts());
            contact.setGallery(extraInfoView.getGalleryImages());
            boolean isCreated = contact.getId() == null;
            repository.save(contact);
            Notifications.create()
                    .title(isCreated ? "Info: Creación de contacto" : "Info: Actualización de contacto")
                    .text(isCreated ? "Contacto creado" : "Contacto actualizado")
                    .graphic(new FontIcon(FontAwesomeSolid.CHECK_CIRCLE))
                    .show();
            goBack(null);
        }
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
