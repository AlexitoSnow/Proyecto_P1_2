package com.espol.contacts.ui.screens.home.fragments;

import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.fontawesome6.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ContactCell extends HBox implements Initializable {
    @FXML
    private MenuItem favoriteMenuItem;
    @FXML
    private FontIcon profileIcon;
    @FXML
    private ImageView profileImage;
    @FXML
    private MenuButton trailing;
    @FXML
    private Label nameLabel;
    @FXML
    private Tooltip nameTooltip;

    private ContactsRepository repository;
    private Contact contact;

    private static final Logger LOGGER = Logger.getLogger(ContactCell.class.getName());

    public ContactCell(Contact contact) {
        repository = ContactsRepositoryImpl.getInstance();
        this.contact = contact;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContactCell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("No se pudo cargar el FXML para ContactCell", exception);
        }
    }

    // TODO: Fix the displayName logic, the lastName should not be appended if it is null
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setPadding(new Insets(8));
        HBox.setHgrow(this, Priority.ALWAYS);

        String displayName = this.contact.getName();
        if (this.contact instanceof Person) {
            final Person person = (Person) this.contact;
            displayName = displayName + " " + person.getLastName();
        }
        nameLabel.setText(displayName);
        nameTooltip.setText(displayName);

        if (contact.getProfilePicture() != null) {
            // TODO: Add the profile picture to profileImage ImageView
            this.getChildren().set(0, profileImage);
        }

        favoriteMenuItem.setText(contact.isFavorite() ? "Remover de favoritos": "Agregar a favoritos");
        favoriteMenuItem.setGraphic(new FontIcon(contact.isFavorite() ? FontAwesomeRegular.STAR : FontAwesomeSolid.STAR));
    }

    @FXML
    void onDelete(ActionEvent event) {
        LOGGER.info("onDeleteContact selected: " + this.contact.getId());
        repository.delete(this.contact);
    }

    @FXML
    void onEdit(ActionEvent event) {
        LOGGER.info("onEditContact selected: " + this.contact.getId());
        AppRouter.setRoot(Routes.FORM, this.contact);
    }

    @FXML
    void toggleFavorite(ActionEvent event) {
        LOGGER.info("onToggleFavorite selected: " + this.contact.getId());
        this.contact.setFavorite(!this.contact.isFavorite());
        final boolean isFavorite = this.contact.isFavorite();
        if (isFavorite) {
            favoriteMenuItem.setText("Remover de favoritos");
            favoriteMenuItem.setGraphic(new FontIcon(FontAwesomeRegular.STAR));
        } else {
            favoriteMenuItem.setText("Agregar a favoritos");
            favoriteMenuItem.setGraphic(new FontIcon(FontAwesomeSolid.STAR));
        }
        repository.save(this.contact);
    }

    public Contact getContact() {
        return contact;
    }

    public void setSelected(boolean selected) {
        if (selected) {
            this.getStyleClass().add("contact-cell-selected");
        } else {
            this.getStyleClass().remove("contact-cell-selected");
        }
    }
}
