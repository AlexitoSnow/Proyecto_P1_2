package com.espol.contacts.ui.screens.home.fragments;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.domain.entity.Company;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import com.espol.contacts.ui.fragments.attributeField.AttributeViewField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;

public class ContactView extends VBox {

    private ContactView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(16);
        this.setPadding(new Insets(16));
    }

    public static ContactViewBuilder builder() {
        return new ContactViewBuilder();
    }

    public static class ContactViewBuilder {
        private ContactView contactView;

        public ContactViewBuilder companyBuilder(Company contact) {
            this.contactView = new ContactView();
            contactView.getChildren().add(new AttributeViewField("Nombre de la Compañía", contact.getName(), Icons.COMPANY));
            contactView.getChildren().add(new AttributeViewField("Industria", contact.getIndustry().toString(), Icons.INDUSTRY));
            addCommonAttributes(contact);
            return this;
        }

        public ContactViewBuilder personBuilder(Person contact) {
            this.contactView = new ContactView();
            contactView.getChildren().add(new AttributeViewField("Primer Nombre", contact.getName(), Icons.R_USER));
            contactView.getChildren().add(new AttributeViewField("Segundo Nombre", contact.getMiddleName(), null));
            contactView.getChildren().add(new AttributeViewField("Apellido", contact.getLastName(), null));
            addCommonAttributes(contact);
            return this;
        }

        private void addCommonAttributes(Contact contact) {
            contactView.getChildren().addAll(contact.getPhones()
                    .stream().map(phone -> new AttributeViewField(
                            phone.getType().toString(), phone.getNumber(), Icons.PHONE))
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getEmails()
                    .stream().map(email -> {
                        final AttributeViewField emailField = new AttributeViewField(email.getType().toString(), email.getEmail(), Icons.MAIL);
                        emailField.setOnIconPressed(event -> {
                            try {
                                Desktop.getDesktop().mail(URI.create("mailto:" + email.getEmail()));
                            } catch (IOException e) {}
                        });
                        return emailField;
                    })
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getAddresses()
                    .stream().map(address -> {
                        final AttributeViewField addressField = new AttributeViewField(address.getType().toString(), address.toString() == null || address.toString().isEmpty() ? address.getMapUrl() : address.toString(), Icons.LOCATION);
                        addressField.setOnIconPressed(event -> {
                            try {
                                Desktop.getDesktop().browse(URI.create(address.getMapUrl()));
                            } catch (IOException e) {}
                        });
                        return addressField;
                    })
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getDates()
                    .stream().map(date -> new AttributeViewField(
                            date.getType().toString(), date.toString(), Icons.CALENDAR))
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getSocialMedias()
                    .stream().map(media -> new AttributeViewField(
                            media.getPlatform().toString(), media.getUsername(), media.getPlatform().getIcon()))
                    .collect(Collectors.toList()));
            contactView.getChildren().add(new AttributeViewField("Notas", contact.getNotes(), Icons.NOTES));
        }

        public ContactView build() {
            return contactView;
        }
    }
}
