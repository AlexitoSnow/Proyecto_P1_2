package com.espol.contacts.ui.fragments;

import com.espol.contacts.domain.entity.Company;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.fontawesome6.FontAwesomeRegular;
import org.kordamp.ikonli.material2.Material2MZ;

import java.util.stream.Collectors;

import static org.kordamp.ikonli.material2.Material2AL.LOCATION_ON;

public class ContactView extends VBox {

    private ContactView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(8));
    }

    public static ContactViewBuilder builder() {
        return new ContactViewBuilder();
    }

    public static class ContactViewBuilder {
        private ContactView contactView;

        public ContactViewBuilder companyBuilder(Company contact) {
            this.contactView = new ContactView();
            contactView.getChildren().add(new AttributeField("Company Name", contact.getName(), FontAwesomeRegular.BUILDING));
            addCommonAttributes(contact);
            return this;
        }

        public ContactViewBuilder personBuilder(Person contact) {
            this.contactView = new ContactView();
            contactView.getChildren().add(new AttributeField("First Name", contact.getName(), FontAwesomeRegular.USER));
            contactView.getChildren().add(new AttributeField("Middle Name", contact.getMiddleName(), null));
            contactView.getChildren().add(new AttributeField("Last Name", contact.getLastName(), null));
            addCommonAttributes(contact);
            return this;
        }

        private void addCommonAttributes(Contact contact) {
            contactView.getChildren().addAll(contact.getPhones()
                    .stream().map(phone -> new AttributeField(
                            phone.getType().name(), phone.getNumber(), Material2MZ.PHONE))
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getEmails()
                    .stream().map(email -> new AttributeField(
                            email.getType().name(), email.getEmail(), Material2MZ.MAIL))
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getAddresses()
                    .stream().map(address -> new AttributeField(
                            address.getType().name(), address.toString(), LOCATION_ON))
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getDates()
                    .stream().map(date -> new AttributeField(
                            date.getType().name(), date.toString(), Material2MZ.PERM_CONTACT_CALENDAR))
                    .collect(Collectors.toList()));
            contactView.getChildren().addAll(contact.getSocialMedias()
                    .stream().map(media -> new AttributeField(
                            media.getPlatform().name(), media.getUsername(), media.getPlatform().getIcon()))
                    .collect(Collectors.toList()));
            contactView.getChildren().add(new AttributeField("Notas", contact.getNotes(), Material2MZ.NOTES));
        }

        public ContactView build() {
            return contactView;
        }
    }
}
