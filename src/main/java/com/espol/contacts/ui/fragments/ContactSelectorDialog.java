package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class ContactSelectorDialog extends Dialog<Contact> {

    private static final ContactsRepository repository = ContactsRepositoryImpl.getInstance();

    public ContactSelectorDialog(Contact main) {
        super();
        setTitle("Seleccionar contacto");
        setHeaderText("Seleccione un contacto relacionado");

        TextField searchField = new TextField();
        searchField.setPromptText("Buscar contacto...");

        VBox container = new VBox(10);
        ScrollPane scrollPane = new ScrollPane();
        VBox contactListView = new VBox(4);
        scrollPane.setContent(contactListView);
        scrollPane.setFitToWidth(true);

        container.getChildren().addAll(searchField, scrollPane);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            contactListView.getChildren().clear();
            repository.getAllByName(newValue)
                    .forEach(contact -> {
                        if (!contact.equals(main)) {
                            Button contactButton = new Button(contact.toString());
                            contactButton.setGraphic(new FontIcon(
                                    contact.getContactType() == ContactType.Persona ? Icons.S_USER
                                            : Icons.S_COMPANY
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

        this.getDialogPane().setContent(container);
        this.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.CANCEL) return null;
            return getResult();
        });
        this.setHeight(600);
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    }
}
