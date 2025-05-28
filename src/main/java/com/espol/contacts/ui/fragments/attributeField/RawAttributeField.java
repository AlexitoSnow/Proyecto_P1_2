package com.espol.contacts.ui.fragments.attributeField;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class RawAttributeField extends VBox {

    public RawAttributeField(String hintText, Ikon leadingIcon) {
        this.setSpacing(8);
        this.getChildren().add(appendButton(hintText, leadingIcon));
    }

    public void addAttributeField(String hintText, Ikon leadingIcon) {
        final TextField textField = new TextField();
        final HBox container = new HBox();

        textField.setPromptText(hintText);
        textField.setMaxWidth(Double.MAX_VALUE);

        FontIcon fontIcon = new FontIcon(leadingIcon);
        fontIcon.setIconSize(16);

        Button removeButton = new Button("-");
        removeButton.setOnAction(actionEvent -> this.getChildren().remove(container));

        container.getChildren().addAll(fontIcon, textField, removeButton);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(4);
        HBox.setHgrow(textField, Priority.ALWAYS);
        if (!this.getChildren().isEmpty()) {
            this.getChildren().remove(this.getChildren().size() - 1);
        }
        this.getChildren().addAll(container, appendButton(hintText, leadingIcon));
    }

    private Button appendButton(String hintText, Ikon leadingIcon) {
        Button appendButton = new Button("Agregar " + hintText);
        appendButton.setOnAction(e -> addAttributeField(hintText, leadingIcon));
        appendButton.setMaxWidth(Double.MAX_VALUE);
        return appendButton;
    }
}
