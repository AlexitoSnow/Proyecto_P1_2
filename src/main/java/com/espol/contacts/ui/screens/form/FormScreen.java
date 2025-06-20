package com.espol.contacts.ui.screens.form;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.domain.entity.Company;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.ui.fragments.ExtraInfoView;
import com.espol.contacts.ui.screens.Initializer;
import com.espol.contacts.ui.screens.form.fragments.ContactForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Map;
import java.util.logging.Logger;

public class FormScreen implements Initializer {
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

    private String event;

    private final ContactsRepository repository;

    private static final Logger LOGGER = Logger.getLogger(FormScreen.class.getName());

    public FormScreen() {
        repository = ContactsRepositoryImpl.getInstance();
    }

    @Override
    public void initialize(Map<String, Object> params) {
        if (params.get("type") != null) {
            contactType = (ContactType) params.get("type");
        }
        if (params.get("contact") != null) {
            contact = (Contact) params.get("contact");
            contactType = contact.getContactType();
        }
        formControl = contact != null ? new ContactForm(contact) : new ContactForm(contactType);
        event = contact != null ? "Edición de Contacto" : "Creación de contacto " + contactType.name();
        titleLabel.setText(event);
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
        if (!buildContact()) return;
        try {
            repository.save(contact);
            Notifications.create()
                    .title("Información:")
                    .text(this.event)
                    .graphic(new FontIcon(Icons.S_CHECK_CIRCLE))
                    .show();
            goBack(null);
        } catch (RuntimeException e) {
            Notifications.create()
                    .title("Error")
                    .text("No se pudo guardar el contacto: " + e.getMessage())
                    .graphic(new FontIcon(Icons.S_EXCLAMATION_TRIANGLE))
                    .show();
        }
    }

    private boolean buildContact() {
        LOGGER.info("Building contacto");
        String name = formControl.getName();
        if (name == null || name.isBlank()) {
            Notifications.create()
                    .title("Error: Creación de contacto")
                    .text("El campo nombre es obligatorio")
                    .graphic(new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE))
                    .show();
            return false;
        }

        if (contact != null) contactType = contact.getContactType();

        Contact.ContactBuilder<?> contactBuilder;
        if (contactType == ContactType.Persona) {
            contactBuilder = Person.builder()
                    .name(name)
                    .middleName(formControl.getMiddleName())
                    .lastName(formControl.getLastName())
                    .contactType(ContactType.Persona);
        } else {
            contactBuilder = Company.builder()
                    .name(name)
                    .industry(formControl.getIndustry())
                    .contactType(ContactType.Empresa);
        }

        if (this.contact != null) {
            contactBuilder.id(this.contact.getId());
            contactBuilder.favorite(this.contact.isFavorite());
        } else {
            contactBuilder.favorite(false);
        }

        contactBuilder.notes(formControl.getNotes());
        formControl.getPhones().forEach(contactBuilder::addPhone);
        formControl.getEmails().forEach(contactBuilder::addEmail);
        formControl.getSocialMedias().forEach(contactBuilder::addSocialMedia);
        formControl.getImportantDates().forEach(contactBuilder::addDate);
        formControl.getAddresses().forEach(contactBuilder::addAddress);

        extraInfoView.getRelatedContacts().forEach(contactBuilder::addRelatedContact);

        contact = contactBuilder.build();
        contact = repository.save(contact); // pre saving

        extraInfoView.getGalleryImages(contact.getId()).forEach(contactBuilder::addImage);
        contactBuilder.profilePicture(extraInfoView.getProfilePicture(contact.getId()));
        contactBuilder.id(contact.getId());

        contact = contactBuilder.build();
        LOGGER.info("Contacto construido");
        return true;
    }
}
