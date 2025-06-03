package com.espol.contacts.ui.fragments;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2MZ;

public class ContactListTile extends HBox {
    private final Label nameLabel;
    private final Node leading;
    private final FontIcon trailingIcon;

    public ContactListTile(String contactName, String imageUrl, EventHandler<MouseEvent> onPressed) {
        nameLabel = new Label(contactName);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        trailingIcon = new FontIcon(Material2MZ.MORE_VERT);
        if (imageUrl != null) {
            leading = new ImageView(imageUrl);
        } else {
            leading = new FontIcon(FontAwesomeSolid.USER_CIRCLE);
        }
        this.setPadding(new Insets(8));
        this.setCursor(Cursor.HAND);
        this.getChildren().addAll(leading, nameLabel, trailingIcon);
        this.setOnMousePressed(onPressed);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
    }

}
