package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Icons;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

public class ProfilePicture extends StackPane {
    private final ImageView imageView;
    private boolean isEditable;

    public ProfilePicture(String path, int size, boolean isEditable) {
        this.isEditable = isEditable;
        imageView = new ImageView();
        FontIcon icon = new FontIcon(Icons.USER_CIRCLE);
        FontIcon editIcon = new FontIcon(Icons.EDIT);

        icon.setIconSize(size);
        editIcon.setIconSize(size / 5);

        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        this.setMinSize(size, size);
        this.setPrefSize(size, size);
        this.setMaxSize(size, size);

        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle((double) size / 2, (double) size / 2,radius);
        imageView.setClip(clip);

        if (path != null && !path.isEmpty()) {
            imageView.setImage(new Image("file:///" + path));
        }

        editIcon.setVisible(this.isEditable);
        this.getChildren().addAll(icon, imageView, editIcon);
        StackPane.setAlignment(editIcon, Pos.BOTTOM_RIGHT);
        this.setCursor(isEditable ? Cursor.HAND : Cursor.DEFAULT);

        if (isEditable) {
            this.setOnMouseClicked(event -> {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Selecciona foto de perfil");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
                var file = chooser.showOpenDialog(this.getScene().getWindow());
                if (file != null) {
                    String imagePath = file.getAbsolutePath();
                    setImage(imagePath);
                }
            });
        }
    }

    public ProfilePicture(String path, int size) {
        this(path, size, false);
    }

    public void setImage(String imagePath) {
        imageView.setImage(new Image("file:///" + imagePath));
    }

    public String getImage() {
        if (imageView.getImage() != null) {
            return imageView.getImage().getUrl().replaceFirst("file:", "");
        }
        return null;
    }
}
