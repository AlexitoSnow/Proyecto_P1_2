package com.espol.contacts.ui.fragments;

import com.espol.contacts.domain.entity.enums.PhoneType;
import com.espol.contacts.ui.fragments.attributeField.AttributeFormField;
import com.espol.contacts.ui.fragments.attributeField.RawAttributeField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.material2.Material2MZ;

import java.util.Arrays;

public class ContactForm extends VBox {
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phone;
    private Button save;

    public ContactForm() {
        firstName = new TextField();
        lastName = new TextField();
        email = new TextField();
        phone = new TextField();
        save = new Button("Guardar");

        firstName.setPromptText("Primer Nombre");
        lastName.setPromptText("Apellido");
        email.setPromptText("Email");
        phone.setPromptText("Celular");

        AttributeFormField attributeFormField = new AttributeFormField("Celular", Arrays.stream(PhoneType.values()).map(PhoneType::name).toArray(String[]::new), Material2MZ.PHONE);
        RawAttributeField raw = new RawAttributeField("Celular", Material2MZ.NOTE);

        this.getChildren().addAll(firstName, lastName, email, phone, attributeFormField, raw, save);
        this.setSpacing(10);
        this.setPadding(new Insets(16));
        this.setAlignment(Pos.CENTER);
    }
}