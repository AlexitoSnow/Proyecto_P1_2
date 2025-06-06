package com.espol.contacts.ui.fragments.home;

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
    private final Button removeButton;

    public RelatedContactBox() {
        super(4.0); // spacing

        this.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        this.setMaxWidth(Double.MAX_VALUE);

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Relationship.values());

        phoneField = new TextField();
        HBox.setHgrow(phoneField, javafx.scene.layout.Priority.ALWAYS);

        removeButton = new Button(null, new FontIcon("fas-minus"));
        removeButton.setCursor(javafx.scene.Cursor.HAND);

        this.getChildren().addAll(choiceBox, phoneField, removeButton);
    }

    public Relationship getSelectedRelationship() {
        return choiceBox.getValue();
    }

    public String getPhoneNumber() {
        return phoneField.getText();
    }

    public void setOnRemoveAction(EventHandler<ActionEvent> handler) {
        removeButton.setOnAction(handler);
    }
}
