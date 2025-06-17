package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Icons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.logging.Logger;

public class ImageGallery extends StackPane {
    private ImageView imageView;
    private Button removeButton;

    private static final Logger LOGGER = Logger.getLogger(ImageGallery.class.getName());

    public ImageGallery(String path, boolean isEditable) {
        imageView = new ImageView();
        imageView.setImage(new Image("file:///" + path));
        if (isEditable) {
            removeButton = new Button(null, new FontIcon(Icons.S_REMOVE));
            removeButton.setCursor(Cursor.HAND);
            this.getChildren().addAll(imageView, removeButton);
            StackPane.setAlignment(removeButton, Pos.TOP_RIGHT);
        } else {
            imageView.setCursor(Cursor.HAND);
            this.getChildren().add(imageView);
        }
        this.setPrefSize(100, 100);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
    }

    public void setOnShow(EventHandler<MouseEvent> event) {
        this.setOnMouseClicked(event);
    }

    public void setOnRemove(EventHandler<ActionEvent> event) {
        if (removeButton != null) {
            removeButton.setOnAction(event);
        }
    }

    public String getImage() {
        return imageView.getImage().getUrl().replaceFirst("file:", "");
    }
}
