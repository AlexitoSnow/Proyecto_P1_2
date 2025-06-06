package com.espol.contacts.ui.fragments.home;

import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class ExtraInfoView extends ScrollPane {
    @FXML
    private FontIcon editProfileButton;
    @FXML
    private FontIcon favoriteIcon;
    @FXML
    private Button galleryButton;
    @FXML
    private FlowPane imageGallery;
    @FXML
    private StackPane profilePicture;
    @FXML
    private HBox quickActionsToolbar;
    @FXML
    private VBox relatedContacts;
    @FXML
    private Button relatedContactsButton;
    @FXML
    private TitledPane galleryTitlePane;
    @FXML
    private TitledPane relatedTitlePane;
    @FXML
    private ToggleButton favoriteButton;

    private Contact contact;
    private ContactsRepository repository;
    private boolean isEditable;

    public ExtraInfoView(Contact contact, boolean isEditable) {
        this.contact = contact;
        this.isEditable = isEditable;
        this.repository = ContactsRepositoryImpl.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExtraInfoView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("No se pudo cargar el FXML para ExtraInfoView", exception);
        }
    }

    @FXML
    void initialize() {
        if (isEditable) { // Modo edición
            editProfileButton.setVisible(true);
            editProfileButton.setManaged(true);

            quickActionsToolbar.setVisible(false);
            quickActionsToolbar.setManaged(false);

            profilePicture.setCursor(Cursor.HAND);

            galleryButton.setVisible(true);
            galleryButton.setManaged(true);
            relatedContactsButton.setVisible(true);
            relatedContactsButton.setManaged(true);

        } else { // Modo vista
            editProfileButton.setVisible(false);
            editProfileButton.setManaged(false);

            quickActionsToolbar.setVisible(true);
            quickActionsToolbar.setManaged(true);

            profilePicture.setCursor(null);

            galleryButton.setVisible(false);
            galleryButton.setManaged(false);
            relatedContactsButton.setVisible(false);
            relatedContactsButton.setManaged(false);

            favoriteIcon.setIconLiteral(contact.isFavorite() ? "fas-star" : "far-star");
            favoriteButton.setSelected(contact.isFavorite());

            //galleryTitlePane.setExpanded(!contact.getPictures().isEmpty());
            relatedTitlePane.setExpanded(!contact.getRelatedContacts().isEmpty());
        }
    }

    @FXML
    void onAddGallery(ActionEvent event) {
        galleryTitlePane.setExpanded(true);
    }

    @FXML
    void onAddRelated(ActionEvent event) {
        RelatedContactBox relatedContactBox = new RelatedContactBox();
        relatedContactBox.setOnRemoveAction(e -> {
            relatedContacts.getChildren().remove(relatedContactBox);
        });
        relatedContacts.getChildren().add(relatedContactBox);
        relatedTitlePane.setExpanded(true);
    }

    @FXML
    void onEditProfile(MouseEvent event) {
        if (!isEditable) return;
    }

    @FXML
    void onToggleFavorite(ActionEvent event) {
        contact.setFavorite(!contact.isFavorite());
        favoriteIcon.setIconLiteral(contact.isFavorite() ? "fas-star" : "far-star");
        repository.save(contact);
    }

    @FXML
    void goToEdit(ActionEvent event) {
        AppRouter.setRoot(Routes.EDIT_CONTACT, contact);
    }

    @FXML
    void onDelete(ActionEvent event) {
        repository.delete(contact);
    }
}
