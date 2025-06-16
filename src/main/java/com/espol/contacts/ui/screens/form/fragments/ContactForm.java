package com.espol.contacts.ui.screens.form.fragments;

import com.espol.contacts.domain.entity.*;
import com.espol.contacts.domain.entity.enums.*;
import com.espol.contacts.ui.fragments.attributeField.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.kordamp.ikonli.material2.Material2MZ.PLUS;

public class ContactForm extends VBox {
    private final Logger LOGGER = Logger.getLogger(ContactForm.class.getName());
    private final ContactType contactType;
    private Contact contact;

    // Campos básicos
    private SimpleFormField nameField;
    private SimpleFormField middleNameField;
    private SimpleFormField lastNameField;
    private SimpleFormField notesField;
    private ChoiceFormField<IndustryType> industryField;

    // TitledPanes
    private final TitledPane phonesTitledPane;
    private final TitledPane emailsTitledPane;
    private final TitledPane addressesTitledPane;
    private final TitledPane datesTitledPane;
    private final TitledPane socialMediaTitledPane;

    public ContactForm(Contact contact) {
        this(contact.getContactType(), contact);
    }

    public ContactForm(ContactType contactType) {
        this(contactType, null);
    }

    private ContactForm(ContactType contactType, Contact contactToEdit) {
        this.contactType = contactType;
        this.contact = contactToEdit;

        this.setSpacing(10);
        this.setPadding(new Insets(16));
        this.setAlignment(Pos.CENTER);
        phonesTitledPane = buildTitledPane("Teléfonos");
        emailsTitledPane = buildTitledPane("E-mails");
        addressesTitledPane = buildTitledPane("Direcciones");
        datesTitledPane = buildTitledPane("Fechas Importantes");
        socialMediaTitledPane = buildTitledPane("Redes Sociales");

        initializeForm();

        if (this.contact != null) loadContactData();
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
        button.setTooltip(new Tooltip("Agregar " + titledPane.getText()));
        button.setOnAction(e -> {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.MINUS));
            removeButton.getStyleClass().add("text-icon-button");
            BaseFormField field;
            removeButton.setTooltip(new Tooltip("Remover"));
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
                    field = new AddressFormField();
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
            industryField = new ChoiceFormField<>("Industria", IndustryType.values(), FontAwesomeSolid.INDUSTRY);
            this.getChildren().addAll(nameField, industryField);
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

    private void loadContactData() {
        // Basic fields
        nameField.setValue(contact.getName());
        notesField.setValue(contact.getNotes());

        if (contactType == ContactType.Persona && contact instanceof Person) {
            Person person = (Person) contact;
            middleNameField.setValue(person.getMiddleName());
            lastNameField.setValue(person.getLastName());
        } else {
            industryField.setValue(((Company) contact).getIndustry());
        }

        // Phones
        for (Phone phone : contact.getPhones()) {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.TRASH));
            removeButton.getStyleClass().add("text-icon-button");
            removeButton.setTooltip(new Tooltip("Eliminar"));
            VBox container = (VBox) phonesTitledPane.getContent();
            removeButton.setOnAction(ev -> container.getChildren().remove(row));

            TypeFormField<PhoneType> field = new TypeFormField<>("Teléfono", PhoneType.values(), FontAwesomeSolid.PHONE);
            field.setType(phone.getType());
            field.setValue(phone.getNumber());

            row.setAlignment(Pos.CENTER_LEFT);
            row.getChildren().addAll(field, removeButton);
            row.setPadding(new Insets(0, 8, 0, 8));
            HBox.setHgrow(field, Priority.ALWAYS);
            container.getChildren().add(row);
        }
        if (!contact.getPhones().isEmpty()) phonesTitledPane.setExpanded(true);

        // Emails (similar logic to phones)
        for (Email email : contact.getEmails()) {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.TRASH));
            removeButton.getStyleClass().add("text-icon-button");
            removeButton.setTooltip(new Tooltip("Eliminar"));
            VBox container = (VBox) emailsTitledPane.getContent();
            removeButton.setOnAction(ev -> container.getChildren().remove(row));

            TypeFormField<EmailType> field = new TypeFormField<>("Email", EmailType.values(), FontAwesomeSolid.ENVELOPE);
            field.setType(email.getType());
            field.setValue(email.getEmail());

            row.setAlignment(Pos.CENTER_LEFT);
            row.getChildren().addAll(field, removeButton);
            row.setPadding(new Insets(0, 8, 0, 8));
            HBox.setHgrow(field, Priority.ALWAYS);
            container.getChildren().add(row);
        }
        if (!contact.getEmails().isEmpty()) emailsTitledPane.setExpanded(true);

        // Addresses (using your new AddressFormField)
        for (Address address : contact.getAddresses()) {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.TRASH));
            removeButton.getStyleClass().add("text-icon-button");
            removeButton.setTooltip(new Tooltip("Eliminar"));
            VBox container = (VBox) addressesTitledPane.getContent();
            removeButton.setOnAction(ev -> container.getChildren().remove(row));

            AddressFormField field = new AddressFormField(); // Instantiate your AddressFormField
            field.setValue(address); // Call the setter on AddressFormField

            row.setAlignment(Pos.TOP_LEFT); // Ensure alignment is good for multiline AddressFormField
            row.getChildren().addAll(field, removeButton);
            row.setPadding(new Insets(0, 8, 0, 8));
            HBox.setHgrow(field, Priority.ALWAYS);
            container.getChildren().add(row);
        }
        if (!contact.getAddresses().isEmpty()) addressesTitledPane.setExpanded(true);


        // Dates (similar logic)
        for (ImportantDate date : contact.getDates()) {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.TRASH));
            removeButton.getStyleClass().add("text-icon-button");
            removeButton.setTooltip(new Tooltip("Eliminar"));
            VBox container = (VBox) datesTitledPane.getContent();
            removeButton.setOnAction(ev -> container.getChildren().remove(row));

            DateFormField field = new DateFormField();
            field.setValue(date);

            row.setAlignment(Pos.CENTER_LEFT);
            row.getChildren().addAll(field, removeButton);
            row.setPadding(new Insets(0, 8, 0, 8));
            HBox.setHgrow(field, Priority.ALWAYS);
            container.getChildren().add(row);
        }
        if (!contact.getDates().isEmpty()) datesTitledPane.setExpanded(true);


        // Social Media (similar logic)
        for (SocialMedia socialMedia : contact.getSocialMedias()) {
            final HBox row = new HBox(4);
            final Button removeButton = new Button(null, new FontIcon(FontAwesomeSolid.TRASH));
            removeButton.getStyleClass().add("text-icon-button");
            removeButton.setTooltip(new Tooltip("Eliminar"));
            VBox container = (VBox) socialMediaTitledPane.getContent();
            removeButton.setOnAction(ev -> container.getChildren().remove(row));

            TypeFormField<SocialPlatform> field = new TypeFormField<>("Red Social", SocialPlatform.values(), Arrays.stream(SocialPlatform.values()).map(SocialPlatform::getIcon).toArray(Ikon[]::new));
            field.setType(socialMedia.getPlatform());
            field.setValue(socialMedia.getUsername());

            row.setAlignment(Pos.CENTER_LEFT);
            row.getChildren().addAll(field, removeButton);
            row.setPadding(new Insets(0, 8, 0, 8));
            HBox.setHgrow(field, Priority.ALWAYS);
            container.getChildren().add(row);
        }
        if (!contact.getSocialMedias().isEmpty()) socialMediaTitledPane.setExpanded(true);
    }

    public Contact getContact() {
        return contact;
    }

    public String getName() {
        return nameField.getValue();
    }

    public String getMiddleName() {
        return middleNameField.getValue();
    }

    public String getLastName() {
        return lastNameField.getValue();
    }

    public IndustryType getIndustry() {
        return industryField.getValue();
    }

    public String getNotes() {
        return notesField.getValue();
    }

    public Set<Phone> getPhones() {
        final ObservableList<Node> fields = ((VBox) phonesTitledPane.getContent()).getChildren();
        return fields.stream().map(field -> {
            final TypeFormField<PhoneType> formField = (TypeFormField<PhoneType>) ((HBox) field).getChildren().get(0);
            return new Phone(formField.getType(), formField.getValue());
        }).collect(Collectors.toSet());
    }

    public Set<Email> getEmails() {
        final ObservableList<Node> fields = ((VBox) emailsTitledPane.getContent()).getChildren();
        return fields.stream().map(field -> {
            final TypeFormField<EmailType> formField = (TypeFormField<EmailType>) ((HBox) field).getChildren().get(0);
            return new Email(formField.getType(), formField.getValue());
        }).collect(Collectors.toSet());
    }

    public Set<SocialMedia> getSocialMedias() {
        final ObservableList<Node> fields = ((VBox) socialMediaTitledPane.getContent()).getChildren();
        return fields.stream().map(field -> {
            final TypeFormField<SocialPlatform> formField = (TypeFormField<SocialPlatform>) ((HBox) field).getChildren().get(0);
            return new SocialMedia(formField.getValue(), formField.getType());
        }).collect(Collectors.toSet());
    }

    public Set<ImportantDate> getImportantDates() {
        final ObservableList<Node> fields = ((VBox) datesTitledPane.getContent()).getChildren();
        return fields.stream().map(field -> {
            final DateFormField formField = (DateFormField) ((HBox) field).getChildren().get(0);
            return formField.getValue();
        }).collect(Collectors.toSet());
    }

    public Set<Address> getAddresses() {
        final ObservableList<Node> fields = ((VBox) addressesTitledPane.getContent()).getChildren();
        return fields.stream().map(field -> {
            final AddressFormField formField = (AddressFormField) ((HBox) field).getChildren().get(0);
            return formField.getValue();
        }).filter(address -> address != null).collect(Collectors.toSet());
    }
}