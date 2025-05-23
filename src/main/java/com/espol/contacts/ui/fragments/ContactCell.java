package com.espol.contacts.ui.fragments;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.repository.ContactsRepository;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.logging.Logger;

import static org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.USER_CIRCLE;
import static org.kordamp.ikonli.material2.Material2MZ.MORE_VERT;

public class ContactCell extends ListCell<Contact> {
    private static final Logger LOGGER = Logger.getLogger(ContactCell.class.getName());
    private HBox container;
    private final ContactContextMenu contactContextMenu;
    private final ContactsRepository repository;
    private final IconButton trailing;
    private final ImageView contactImage;
    private final Label contactText;
    private final FontIcon leading;
    private final Tooltip tooltip;

    public ContactCell(ContactsRepository repository) {
        contactContextMenu = new ContactContextMenu();
        this.repository = repository;
        this.contactImage = new ImageView();
        this.container = new HBox(8);
        this.tooltip = new Tooltip();

        this.setContextMenu(contactContextMenu);

        contactContextMenu.deleteMenuItem.setOnAction(event -> {
            LOGGER.info("onDeleteContact selected: " + getItem().getId());
        });
        contactContextMenu.editMenuItem.setOnAction(event -> {
            LOGGER.info("onEditContact selected: " + getItem().getId());
        });
        contactContextMenu.favoriteMenuItem.setOnAction(event -> {
            LOGGER.info("onToggleFavorite selected: " + getItem().getId());
            getItem().setFavorite(!getItem().isFavorite());
            contactContextMenu.toggleFavorite(!getItem().isFavorite());
            repository.save(getItem());
        });

        trailing = new IconButton(MORE_VERT, 20);
        trailing.setOnMouseClicked(event -> {
            contactContextMenu.show(this, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        leading = new FontIcon(USER_CIRCLE);
        leading.setIconSize(60);
        trailing.setMaxHeight(Double.MAX_VALUE);
        trailing.setCursor(Cursor.HAND);

        contactText = new Label();
        contactText.setTextOverrun(OverrunStyle.ELLIPSIS);

        container.getChildren().addAll(leading, contactText, trailing);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setMaxWidth(Double.MAX_VALUE);

        Platform.runLater(() -> {
            Scene scene = this.getScene();
            if (scene == null) return;
            contactText.setMaxWidth(scene.getWidth() * 0.18);
        });

        this.setTooltip(tooltip);
    }

    @Override
    protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        if (empty || contact == null) {
            setGraphic(null);
            setText(null);
        } else {
            contactContextMenu.toggleFavorite(contact.isFavorite());
            contactText.setText(getItem().getName());
            tooltip.setText(getItem().getName());

            //if (contact.getImage() != null) {
            if (false) {
                contactImage.setImage(new Image("/images/contact_icon.png"));
                container.getChildren().set(0, contactImage);
            } else {
                container.getChildren().set(0, leading);
            }

            setGraphic(container);
            setText(null);
        }
    }
}
