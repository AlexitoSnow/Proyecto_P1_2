package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class ContactSelectorDialog extends Dialog<Contact> {

    private static final ContactsRepository repository = ContactsRepositoryImpl.getInstance();

    public ContactSelectorDialog(Contact main) {
        super();
        setTitle("Seleccionar contacto");

        TextField searchField = new TextField();
        searchField.setPromptText("Buscar contacto...");
        searchField.setMaxWidth(Double.MAX_VALUE);
        this.getDialogPane().setHeader(searchField);

        ScrollPane scrollPane = new ScrollPane();
        VBox contactListView = new VBox(4);
        contactListView.setPadding(new Insets(8));
        scrollPane.setContent(contactListView);
        scrollPane.setFitToWidth(true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            contactListView.getChildren().clear();
            repository.getAllByName(newValue)
                    .forEach(contact -> {
                        if (!contact.equals(main)) {
                            Button contactButton = new Button(contact.toString());
                            contactButton.setGraphic(new FontIcon(
                                    contact.getContactType() == ContactType.Persona ? Icons.S_USER
                                            : Icons.COMPANY
                            ));
                            contactButton.setAlignment(Pos.CENTER_LEFT);
                            contactButton.setMaxWidth(Double.MAX_VALUE);
                            contactButton.setOnAction(e -> {
                                setResult(contact);
                                Platform.runLater(this::close);
                            });
                            contactListView.getChildren().add(contactButton);
                        }
                    });
        });

        this.getDialogPane().setContent(scrollPane);
        this.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.CANCEL) return null;
            return getResult();
        });

        this.setHeight(500);
        this.setWidth(300);
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        this.getDialogPane().setPadding(new Insets(8));
    }
}
