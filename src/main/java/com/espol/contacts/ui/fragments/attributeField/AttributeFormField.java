package com.espol.contacts.ui.fragments.attributeField;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class AttributeFormField extends HBox {
    private String selectedType;

    public AttributeFormField(String hintText, String[] types, Ikon leadingIcon) {
        final TextField textField = new TextField();
        final ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(types);
        choiceBox.setValue(types[0]);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((o, old, newValue) -> {
            if (newValue != null) {
                selectedType = newValue;
            }
            System.out.println(selectedType);
        });
        choiceBox.getStyleClass().add("text-icon-button");

        textField.setPromptText(hintText);
        textField.setMaxWidth(Double.MAX_VALUE);

        final VBox vbox = new VBox(choiceBox, textField);

        FontIcon fontIcon = new FontIcon(leadingIcon);
        fontIcon.setIconSize(16);

        this.getChildren().addAll(fontIcon, vbox);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(4);
        HBox.setHgrow(vbox, Priority.ALWAYS);
    }
}
