package com.espol.contacts.ui.fragments;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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

        this.getChildren().addAll(firstName, lastName, email, phone, save);
        this.setSpacing(10);
        this.setPadding(new Insets(16));
        this.setAlignment(Pos.CENTER);
    }
}