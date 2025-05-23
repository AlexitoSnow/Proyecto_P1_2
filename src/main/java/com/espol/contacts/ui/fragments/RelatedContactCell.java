package com.espol.contacts.ui.fragments;

import com.espol.contacts.domain.entity.Contact;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import static org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.*;
import static org.kordamp.ikonli.material2.Material2MZ.REMOVE;

/**
 * TODO: Not ready for production (Mock only)
 */
public class RelatedContactCell extends HBox {
    private Contact contact;
    private final ImageView contactImage;
    private final IconButton trailingButton;
    private final Label title;
    private final MenuButton relationMenu;

    public RelatedContactCell(Contact contact) {
        this.contact = contact;
        this.trailingButton = new IconButton(REMOVE, 16);
        this.contactImage = new ImageView();
        this.contactImage.setFitWidth(30);
        this.contactImage.setFitHeight(30);
        this.title = new Label(contact.getName());
        this.relationMenu = new MenuButton("Relación");
        this.relationMenu.getStyleClass().add("icon-text-button");

        relationMenu.getItems().addAll(
                new Menu("Persona", new FontIcon(USER),
                        new MenuItem("Madre"),
                        new MenuItem("Tío"),
                        new MenuItem("Abuelo"),
                        new MenuItem("Otro")
                ),
                new Menu("Empresa", new FontIcon(BUILDING),
                        new MenuItem("Jefe"),
                        new MenuItem("Empleado"),
                        new MenuItem("Otro")
                )
        );

        final FontIcon leading = new FontIcon(USER_CIRCLE);
        leading.setIconSize(24);
        final VBox col = new VBox(title, relationMenu);
        final HBox row = new HBox(4, leading, col);
        row.setAlignment(Pos.CENTER_LEFT);

        var infoButton = new Button(null, row);
        infoButton.getStyleClass().add("text-icon-button");
        infoButton.setMaxWidth(Double.MAX_VALUE);
        infoButton.setCursor(Cursor.HAND);

        this.getChildren().addAll(infoButton, trailingButton);
        this.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(infoButton, Priority.ALWAYS);
    }

}
