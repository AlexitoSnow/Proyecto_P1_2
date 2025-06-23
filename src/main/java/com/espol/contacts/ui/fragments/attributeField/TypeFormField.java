package com.espol.contacts.ui.fragments.attributeField;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TypeFormField<K> extends BaseFormField<String> {

    private final K[] options;
    private K type;
    private final String[] icons;
    private ChoiceBox<K> choiceBox;

    public TypeFormField(String hintText, K[] options, String leadingIcon) {
        super(hintText, leadingIcon);
        icons = null;
        this.options = options;
        initializeField();
    }

    public TypeFormField(String hintText, K[] options, String[] icons) {
        super(hintText, icons[0]);
        this.icons = icons;
        this.options = options;
        initializeField();
    }

    private void createChoiceBox() {
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(options);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getStyleClass().add("text-icon-button");
        type = choiceBox.getSelectionModel().getSelectedItem();
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            type = newValue;
            if (icons != null) {
                changeIcon(icons[choiceBox.getSelectionModel().getSelectedIndex()]);
            }
        });
    }

    private void createTextField() {
        mainField = new TextField();
        TextField textField = (TextField) mainField;
        textField.setPromptText(hintText);
        textField.setMaxWidth(Double.MAX_VALUE);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            value = newValue;
        });
    }

    @Override
    public void initializeField() {
        createChoiceBox();
        HBox selectionBox = new HBox(leadingIcon, choiceBox);
        createTextField();

        selectionBox.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(selectionBox, mainField);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public void setValue(String value) {
        ((TextField) mainField).setText(value);
        this.value = value;
    }

    public void setType(K type) {
        choiceBox.getSelectionModel().select(type);
        this.type = type;
    }

    public K getType() {
        return type;
    }

    @Override
    public String validate() {
        if (validator == null) return null;
        TextField textField = (TextField) mainField;
        String value = textField.getText();
        return validator.apply(value);
    }
}