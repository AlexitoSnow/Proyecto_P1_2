package com.espol.contacts.ui.fragments.attributeField;

import com.espol.contacts.domain.entity.ImportantDate;
import com.espol.contacts.domain.entity.enums.DateType;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

import static org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.CALENDAR;

public class DateFormField extends BaseFormField<ImportantDate> {

    private final DateType[] options;
    private ChoiceBox<DateType> choiceBox;

    public DateFormField() {
        super("Fecha Importante", CALENDAR);
        this.options = DateType.values();
        value = new ImportantDate();
        initializeField();
    }

    private void createChoiceBox() {
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(options);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getStyleClass().add("text-icon-button");
        value.setType(choiceBox.getSelectionModel().getSelectedItem());
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            value.setType(newValue);
        });
    }

    private void createDatePicker() {
        mainField = new DatePicker(LocalDate.now());
        DatePicker datePicker = (DatePicker) mainField;
        datePicker.setPromptText(hintText);
        datePicker.setEditable(false);
        datePicker.setMaxWidth(Double.MAX_VALUE);
        value.setDate(datePicker.getValue());
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            value.setDate(newValue);
        });
    }

    @Override
    public void initializeField() {
        createChoiceBox();
        HBox selectionBox = new HBox(leadingIcon, choiceBox);

        createDatePicker();

        selectionBox.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(selectionBox, mainField);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public void setValue(ImportantDate value) {
        this.value = value;
        this.choiceBox.getSelectionModel().select(value.getType());
        ((DatePicker) this.mainField).setValue(value.getDate());
    }

    @Override
    public String validate() {
        if (validator == null) return null;
        DatePicker datePicker = (DatePicker) mainField;
        String value = datePicker.getValue().toString();
        return validator.apply(value);
    }
}
