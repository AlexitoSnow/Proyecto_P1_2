package com.espol.contacts.ui.fragments;

import com.espol.contacts.domain.entity.RelatedContact;
import com.espol.contacts.domain.entity.enums.Relationship;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class RelatedContactBox extends HBox {
    private final ChoiceBox<Relationship> choiceBox;
    private final TextField phoneField;
    private Button removeButton;
    private final boolean isEditable;
    private RelatedContact value;

    public RelatedContactBox(boolean isEditable) {
        super(4.0); // spacing
        this.isEditable = isEditable;

        this.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        this.setMaxWidth(Double.MAX_VALUE);

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Relationship.values());
        choiceBox.setValue(Relationship.FRIEND);
        choiceBox.setDisable(!isEditable);

        phoneField = new TextField();
        phoneField.setPromptText("Número de teléfono");
        phoneField.setEditable(isEditable);
        HBox.setHgrow(phoneField, javafx.scene.layout.Priority.ALWAYS);

        this.getChildren().addAll(choiceBox, phoneField);

        if (isEditable) {
            removeButton = new Button(null, new FontIcon("fas-minus"));

            phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                value.setPhoneNumber(newValue);
            });

            choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                value.setRelationship(newValue);
            });

            this.getChildren().add(removeButton);
        }
    }

    public void setOnRemoveAction(EventHandler<ActionEvent> handler) {
        if (removeButton == null) return;
        removeButton.setOnAction(handler);
    }

    public void setValue(RelatedContact relatedContact) {
        this.value = relatedContact;
        if (relatedContact != null) {
            choiceBox.setValue(relatedContact.getRelationship());
            phoneField.setText(relatedContact.getPhoneNumber());
        }
    }

    public RelatedContact getValue() {
        if (value == null) {
            return null;
        }
        value.setRelationship(choiceBox.getValue());
        value.setPhoneNumber(phoneField.getText());
        return value;
    }
}
