package com.espol.contacts.ui.fragments.attributeField;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.Ikon;

public class TypeFormField<K> extends BaseFormField<String> {

    private final K[] options;
    private K type;
    private final Ikon[] ikons;

    public TypeFormField(String hintText, K[] options, Ikon leadingIcon) {
        super(hintText, leadingIcon);
        ikons = null;
        this.options = options;
        initializeField();
    }

    public TypeFormField(String hintText, K[] options, Ikon[] ikons) {
        super(hintText, ikons[0]);
        this.ikons = ikons;
        this.options = options;
        initializeField();
    }

    private ChoiceBox<K> createChoiceBox() {
        ChoiceBox<K> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(options);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getStyleClass().add("text-icon-button");
        type = choiceBox.getSelectionModel().getSelectedItem();
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            type = newValue;
            if (ikons != null) {
                changeIcon(ikons[choiceBox.getSelectionModel().getSelectedIndex()]);
            }
        });
        return choiceBox;
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
        HBox selectionBox = new HBox(leadingIcon, createChoiceBox());
        createTextField();

        selectionBox.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(selectionBox, mainField);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public K getType() {
        return type;
    }
}