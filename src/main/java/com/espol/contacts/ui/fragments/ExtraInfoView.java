package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.RelatedContact;
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
import java.util.Set;

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

            galleryTitlePane.setExpanded(!contact.getGallery().isEmpty());
            relatedTitlePane.setExpanded(!contact.getRelatedContacts().isEmpty());
        }

        setProfilePicture();
        setGallery();
        setRelatedContacts();
    }

    // TODO: Request a image picker to add ImageView instances to the imageGallery FlowPane
    // TODO: Wrap the ImageView into a StackPane
    // TODO: Add a remove button at the top right corner of the StackPane to remove the Stack from the FlowPane
    @FXML
    void onAddGallery(ActionEvent event) {
        galleryTitlePane.setExpanded(true);
    }

    @FXML
    void onAddRelated(ActionEvent event) {
        RelatedContactBox relatedContactBox = new RelatedContactBox(isEditable);
        relatedContactBox.setOnRemoveAction(e -> {
            relatedContacts.getChildren().remove(relatedContactBox);
        });
        relatedContacts.getChildren().add(relatedContactBox);
        relatedTitlePane.setExpanded(true);
    }

    // TODO: Request a image picker to add a profile picture to the profilePicture StackPane
    // TODO: The ImageView will be placed at the index 1 of the StackPane
    @FXML
    void onEditProfile(MouseEvent event) {
        if (!isEditable) return;
        // Add logic after the condition
    }

    @FXML
    void onToggleFavorite(ActionEvent event) {
        contact.setFavorite(!contact.isFavorite());
        favoriteIcon.setIconLiteral(contact.isFavorite() ? Icons.SOLID_STAR :Icons.REGULAR_STAR);
        repository.save(contact);
    }

    @FXML
    void goToEdit(ActionEvent event) {
        AppRouter.setRoot(Routes.FORM, contact);
    }

    @FXML
    void onDelete(ActionEvent event) {
        repository.delete(contact);
    }

    // TODO: Obtain the images from the imageGallery FlowPane and obtain the byte[] from each ImageView
    // TODO: Remember that the imageGallery FlowPane can contain multiple StackPanes, each with an ImageView (we need this) and a remove button
    // TODO: You will need to cast the children of the FlowPane to StackPane and then get the ImageView (index 0) from each StackPane
    public Set<byte[]> getGalleryImages() {
        return null;
    }

    // TODO: Obtain the related contacts from the relatedContacts VBox
    // TODO: RelatedContactBox has a method to get the value
    // TODO: IMPORTANT: You need to search the phone number in the repository to ensure that the related contact exists before adding it to the set
    public Set<RelatedContact> getRelatedContacts() {
        return null;
    }

    // TODO: Obtain the profile picture from the profilePicture StackPane (ImageView at index 1)
    public byte[] getProfilePicture() {
        return null;
    }

    // TODO: Set the profile picture in the profilePicture StackPane (ImageView at index 1)
    // TODO: Remember that the profilePicture could be null, use the contact.getProfilePicture()
    private void setProfilePicture() {
    }

    // TODO: Add the contacts.getGallery() to the imageGallery FlowPane
    private void setGallery() {
        // TODO: In editable mode, we need the remove button on each image (StackPane with ImageView and remove button)
        if (isEditable) {

        } else { // TODO: In view mode, we just need to display the images (ImageView)

        }
    }

    // TODO: Add the contacts.getRelatedContacts() to the relatedContacts VBox
    private void setRelatedContacts() {

    }
}
