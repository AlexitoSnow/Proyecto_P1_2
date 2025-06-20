package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.RelatedContact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.entity.enums.Relationship;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Map;

public class RelatedContactBox extends HBox {
    private final ChoiceBox<Relationship> choiceBox;
    private final Button contactSelectorButton;
    private Button removeButton;
    private RelatedContact value;

    public RelatedContactBox(boolean isEditable, Contact main) {
        super(4.0); // spacing
        this.value = new RelatedContact(null, null);

        this.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        this.setMaxWidth(Double.MAX_VALUE);

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Relationship.values());
        choiceBox.setValue(Relationship.FRIEND);
        choiceBox.setDisable(!isEditable);

        contactSelectorButton = new Button("Seleccionar contacto");
        contactSelectorButton.setMaxWidth(Double.MAX_VALUE);
        contactSelectorButton.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(contactSelectorButton, javafx.scene.layout.Priority.ALWAYS);

        this.getChildren().addAll(choiceBox, contactSelectorButton);

        if (isEditable) {
            contactSelectorButton.setOnAction(event -> {
                ContactSelectorDialog dialog = new ContactSelectorDialog(main);
                Contact result = dialog.showAndWait().orElse(null);
                if (result != null) {
                    contactSelectorButton.setText(result.toString());
                    contactSelectorButton.setGraphic(new FontIcon(
                            result.getContactType() == ContactType.Persona ? Icons.S_USER : Icons.S_COMPANY
                    ));
                    value.setContact(result);
                } else if (value.getContact() == null) {
                    contactSelectorButton.setText("Seleccionar contacto");
                }
            });

            removeButton = new Button(null, new FontIcon(Icons.S_REMOVE));

            choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                value.setRelationship(newValue);
            });

            this.getChildren().add(removeButton);
        } else {
            contactSelectorButton.setOnAction(event -> {
                final int index = ContactsRepositoryImpl.getInstance().getAll().indexOf(value.getContact());
                AppRouter.setRoot(Routes.HOME, Map.of("index", index));
            });
        }
    }

    public void setOnRemoveAction(EventHandler<ActionEvent> handler) {
        if (removeButton == null) return;
        removeButton.setOnAction(handler);
    }

    public void setValue(RelatedContact relatedContact) {
        if (relatedContact != null) {
            this.value = relatedContact;
            choiceBox.setValue(relatedContact.getRelationship());
            contactSelectorButton.setText(relatedContact.getContact().toString());
        }
    }

    public RelatedContact getValue() {
        value.setRelationship(choiceBox.getValue());
        if (value.getContact() == null || value.getRelationship() == null) {
            return null;
        }
        return value;
    }
}
