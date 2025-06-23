package com.espol.contacts.ui.fragments.attributeField;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.domain.entity.Address;
import com.espol.contacts.domain.entity.enums.AddressType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class AddressFormField extends BaseFormField<Address> {

    private final ChoiceBox<AddressType> typeChoiceBox;
    private SimpleFormField streetField;
    private SimpleFormField cityField;
    private SimpleFormField stateField;
    private SimpleFormField postalCodeField;
    private SimpleFormField countryField;
    private SimpleFormField mapUrlField;

    public AddressFormField() {
        super("Nueva dirección", Icons.ADDRESS_BOOK);
        typeChoiceBox = new ChoiceBox<>();
        typeChoiceBox.getItems().addAll(AddressType.values());
        typeChoiceBox.getSelectionModel().selectFirst();
        typeChoiceBox.getStyleClass().add("text-icon-button");

        initializeField();
    }

    @Override
    public Address getValue() {
        AddressType type = typeChoiceBox.getSelectionModel().getSelectedItem();
        String street = streetField.getValue();
        String city = cityField.getValue();
        String state = stateField.getValue();
        String postalCode = postalCodeField.getValue();
        String country = countryField.getValue();
        String mapUrl = mapUrlField.getValue();

        Address build = Address.build()
                .type(type)
                .street(street != null ? street.trim() : null)
                .city(city != null ? city.trim() : null)
                .state(state != null ? state.trim() : null)
                .postalCode(postalCode != null ? postalCode.trim() : null)
                .country(country != null ? country.trim() : null)
                .mapUrl(mapUrl != null ? mapUrl.trim() : null);
        value = mapUrl != null ? build : build.toString() == null || build.toString().isEmpty() ? null : build;
        return value;
    }

    @Override
    public void setValue(Address value) {
        if (value != null) {
            this.value = value;

            typeChoiceBox.getSelectionModel().select(value.getType());
            streetField.setValue(value.getStreet());
            cityField.setValue(value.getCity());
            stateField.setValue(value.getState());
            postalCodeField.setValue(value.getPostalCode());
            countryField.setValue(value.getCountry());
            mapUrlField.setValue(value.getMapUrl());
        }
    }

    @Override
    public void initializeField() {
        streetField = new SimpleFormField("Calle/Número", Icons.ROAD);
        cityField = new SimpleFormField("Ciudad", Icons.CITY);
        stateField = new SimpleFormField("Estado/Provincia", Icons.GLOBE);
        postalCodeField = new SimpleFormField("Código Postal", Icons.MAIL);
        countryField = new SimpleFormField("País", Icons.COUNTRY);
        mapUrlField = new SimpleFormField("URL de Mapa", Icons.MAP_PIN);

        mainField = streetField;

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(8);
        gridPane.setPadding(new Insets(4));

        gridPane.add(streetField, 0, 0, 2, 1);
        gridPane.add(cityField, 0, 1);
        gridPane.add(stateField, 1, 1);
        gridPane.add(postalCodeField, 0, 2);
        gridPane.add(countryField, 1, 2);
        gridPane.add(mapUrlField, 0, 3, 2, 1);

        GridPane.setHgrow(streetField, Priority.ALWAYS);
        GridPane.setHgrow(cityField, Priority.ALWAYS);
        GridPane.setHgrow(stateField, Priority.ALWAYS);
        GridPane.setHgrow(postalCodeField, Priority.ALWAYS);
        GridPane.setHgrow(countryField, Priority.ALWAYS);
        GridPane.setHgrow(mapUrlField, Priority.ALWAYS);

        HBox typeSelectionRow = new HBox(5, leadingIcon, typeChoiceBox);
        typeSelectionRow.setAlignment(Pos.CENTER_LEFT);
        typeSelectionRow.setPadding(new Insets(0, 0, 8, 0));

        this.getChildren().addAll(typeSelectionRow, gridPane);
    }

    @Override
    public String validate() {
        if (validator == null) return null;
        return validator.apply(value.toString());
    }
}
