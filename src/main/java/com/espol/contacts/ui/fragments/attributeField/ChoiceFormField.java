package com.espol.contacts.ui.fragments.attributeField;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.Ikon;

public class ChoiceFormField<T> extends BaseFormField<T> {

    private final T[] options;

    public ChoiceFormField(String hintText, T[] options, String leadingIcon) {
        super(hintText, leadingIcon);
        this.options = options;
        initializeField();
    }

    private void createChoiceBox() {
        mainField = new ChoiceBox<T>();
        ChoiceBox<T> choiceBox = (ChoiceBox<T>) mainField;
        choiceBox.getItems().addAll(options);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getStyleClass().add("text-icon-button");
        value = choiceBox.getSelectionModel().getSelectedItem();
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            value = newValue;
        });
    }

    @Override
    public void initializeField() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(CONTAINER_SPACING);

        createChoiceBox();

        container.getChildren().addAll(leadingIcon, mainField);
        HBox.setHgrow(mainField, Priority.ALWAYS);

        this.getChildren().add(container);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public void setValue(T value) {
        ((ChoiceBox<T>) mainField).getSelectionModel().select(value);
        this.value = value;
    }

    @Override
    public String validate() {
        if (validator == null) return null;
        ChoiceBox<T> choiceBox = (ChoiceBox<T>) mainField;
        T value = choiceBox.getValue();
        return validator.apply(value.toString());
    }
}