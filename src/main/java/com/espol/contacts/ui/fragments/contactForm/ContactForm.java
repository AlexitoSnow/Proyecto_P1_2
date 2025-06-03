package com.espol.contacts.ui.fragments.contactForm;

import com.espol.contacts.domain.entity.*;
import com.espol.contacts.domain.entity.enums.*;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.ui.fragments.attributeField.BaseFormField;
import com.espol.contacts.ui.fragments.attributeField.DateFormField;
import com.espol.contacts.ui.fragments.attributeField.SimpleFormField;
import com.espol.contacts.ui.fragments.attributeField.TypeFormField;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.kordamp.ikonli.material2.Material2MZ.PLUS;

public class ContactForm extends VBox {
    private final Logger LOGGER = Logger.getLogger(ContactForm.class.getName());
    private final ContactType contactType;
    private Contact contact;
    private final ContactsRepository repository;

    // Campos básicos
    private SimpleFormField nameField;
    private SimpleFormField middleNameField;
    private SimpleFormField lastNameField;
    private SimpleFormField notesField;

    // TitledPanes
    private final TitledPane phonesTitledPane;
    private final TitledPane emailsTitledPane;
    private final TitledPane addressesTitledPane;
    private final TitledPane datesTitledPane;
    private final TitledPane socialMediaTitledPane;


    public ContactForm(ContactType contactType) {
        repository = new ContactsRepositoryImpl();
        this.contactType = contactType;
        this.setSpacing(10);
        this.setPadding(new Insets(16));
        this.setAlignment(Pos.CENTER);
        phonesTitledPane = buildTitledPane("Teléfonos");
        emailsTitledPane = buildTitledPane("E-mails");
        addressesTitledPane = buildTitledPane("Direcciones");
        datesTitledPane = buildTitledPane("Fechas Importantes");
        socialMediaTitledPane = buildTitledPane("Redes Sociales");

        initializeForm();
    }

    private TitledPane buildTitledPane(String title) {
        TitledPane titledPane = new TitledPane(title, new VBox(10));
        titledPane.setExpanded(false);
        return titledPane;
    }

    private Button buildAddButton(TitledPane titledPane) {
        final VBox container = (VBox) titledPane.getContent();
        final Button button = new Button(null, new FontIcon(PLUS));
        button.getStyleClass().add("text-icon-button");
        button.setCursor(Cursor.HAND);
        button.setTooltip(new Tooltip("Agregar " + titledPane.getText()));
        button.setOnAction(e -> {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.TRASH));
            removeButton.getStyleClass().add("text-icon-button");
            BaseFormField field;
            removeButton.setTooltip(new Tooltip("Eliminar"));
            removeButton.setOnAction(ev -> container.getChildren().remove(row));

            switch (titledPane.getText()) {
                case "Teléfonos":
                    field = new TypeFormField<>("Teléfono", PhoneType.values(), FontAwesomeSolid.PHONE);
                    break;
                case "E-mails":
                    field = new TypeFormField<>("Email", EmailType.values(), FontAwesomeSolid.ENVELOPE);
                    break;
                case "Fechas Importantes":
                    field = new DateFormField();
                    break;
                case "Redes Sociales":
                    field = new TypeFormField<>("Red Social", SocialPlatform.values(), Arrays.stream(SocialPlatform.values()).map(SocialPlatform::getIcon).toArray(Ikon[]::new));
                    break;
                default:
                    field = new SimpleFormField("Campo", FontAwesomeSolid.USER);
                    break;
            }

            row.setAlignment(Pos.CENTER_LEFT);
            row.getChildren().addAll(field, removeButton);
            row.setPadding(new Insets(0, 8, 0, 8));
            HBox.setHgrow(field, Priority.ALWAYS);

            container.getChildren().add(row);
            titledPane.setExpanded(true);

            PauseTransition delay = new PauseTransition(Duration.millis(100));
            delay.setOnFinished(event -> Platform.runLater(field::requestFocus));
            delay.play();
        });
        return button;
    }

    private StackPane buildPane(TitledPane titledPane) {
        Button button = buildAddButton(titledPane);
        StackPane stackPane = new StackPane(titledPane, button);
        StackPane.setAlignment(button, Pos.TOP_RIGHT);
        StackPane.setMargin(button, new Insets(10, 10, 0, 0));
        return stackPane;
    }

    private void initializeForm() {
        if (contactType == ContactType.Persona) {
            nameField = new SimpleFormField("Primer Nombre", FontAwesomeSolid.USER);
            middleNameField = new SimpleFormField("Segundo Nombre", FontAwesomeSolid.USER);
            lastNameField = new SimpleFormField("Apellido", FontAwesomeSolid.USER);

            this.getChildren().addAll(
                    nameField,
                    middleNameField,
                    lastNameField
            );
        } else {
            nameField = new SimpleFormField("Nombre de la empresa", FontAwesomeSolid.BUILDING);
            this.getChildren().add(nameField);
        }

        // Campo de notas
        notesField = new SimpleFormField("Notas", FontAwesomeSolid.STICKY_NOTE);

        // Agregar campos al formulario
        this.getChildren().addAll(
                buildPane(phonesTitledPane),
                buildPane(emailsTitledPane),
                buildPane(addressesTitledPane),
                buildPane(datesTitledPane),
                buildPane(socialMediaTitledPane),
                notesField
        );

        Platform.runLater(nameField::requestFocus);
    }

    public Contact getContact() {
        if (buildContact()) return contact;
        return null;
    }

    private boolean buildContact() {
        if (nameField.getValue() == null || nameField.getValue().isBlank()) {
            Notifications.create()
                    .title("Error: Creación de contacto")
                    .text("El campo nombre es obligatorio")
                    .graphic(new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE))
                    .show();
            return false;
        }

        Contact.ContactBuilder contactBuilder;
        if (contactType == ContactType.Persona) {
            contactBuilder = Person.builder()
                    .name(nameField.getValue())
                    .middleName(middleNameField.getValue())
                    .lastName(lastNameField.getValue())
                    .contactType(ContactType.Persona);
        } else {
            contactBuilder = Company.builder()
                    .name(nameField.getValue())
                    .contactType(ContactType.Empresa);
        }
        contactBuilder.favorite(false)
                .notes(notesField.getValue());
        final ObservableList<Node> fields = ((VBox) phonesTitledPane.getContent()).getChildren();
        for (Node field : fields) {
            final TypeFormField<PhoneType> formField = (TypeFormField<PhoneType>) ((HBox) field).getChildren().get(0);
            Phone phone = new Phone(formField.getType(), formField.getValue());
            contactBuilder.addPhone(phone);
        }

        final ObservableList<Node> emailFields = ((VBox) emailsTitledPane.getContent()).getChildren();
        for (Node field : emailFields) {
            final TypeFormField<EmailType> formField = (TypeFormField<EmailType>) ((HBox) field).getChildren().get(0);
            Email email = new Email(formField.getType(), formField.getValue());
            contactBuilder.addEmail(email);
        }

        final ObservableList<Node> socialMediaFields = ((VBox) socialMediaTitledPane.getContent()).getChildren();
        for (Node field : socialMediaFields) {
            final TypeFormField<SocialPlatform> formField = (TypeFormField<SocialPlatform>) ((HBox) field).getChildren().get(0);
            SocialMedia media = new SocialMedia(formField.getValue(), formField.getType());
            contactBuilder.addSocialMedia(media);
        }

        final ObservableList<Node> datesFields = ((VBox) datesTitledPane.getContent()).getChildren();
        for (Node field : datesFields) {
            final DateFormField formField = (DateFormField) ((HBox) field).getChildren().get(0);
            contactBuilder.addDate(formField.getValue());
        }
        contact = contactBuilder.build();
        LOGGER.info("Contacto creado");
        return true;
    }
}