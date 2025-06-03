package com.espol.contacts.ui.fragments.attributeField;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.Ikon;

public class SimpleFormField extends BaseFormField<String> {

    public SimpleFormField(String hintText, Ikon leadingIcon) {
        super(hintText, leadingIcon);
        initializeField();
    }

    @Override
    public void initializeField() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(CONTAINER_SPACING);

        createTextField();
        container.getChildren().addAll(leadingIcon, mainField);
        HBox.setHgrow(mainField, Priority.ALWAYS);

        this.getChildren().add(container);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    private void createTextField() {
        mainField = new TextField();
        ((TextField) mainField).setPromptText(hintText);
        ((TextField) mainField).setMaxWidth(Double.MAX_VALUE);
        ((TextField) mainField).textProperty().addListener((observable, oldValue, newValue) -> {
            value = newValue;
        });
    }
}