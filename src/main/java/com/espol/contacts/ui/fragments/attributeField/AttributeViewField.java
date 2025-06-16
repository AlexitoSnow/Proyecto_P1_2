package com.espol.contacts.ui.fragments.attributeField;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class AttributeViewField extends Button {

    private final FontIcon trailing;

    public AttributeViewField(String attributeName, String attributeValue, Ikon trailingIcon) {
        final Label nameText = new Label(attributeName);
        final Label valueText = new Label(attributeValue);

        trailing = trailingIcon == null ? null : new FontIcon(trailingIcon);
        final HBox valueBox = new HBox();
        final Separator separator = new Separator();

        valueBox.getChildren().add(valueText);
        if (trailing != null) {
            trailing.setIconSize(24);
            valueBox.getChildren().add(trailing);
        }

        HBox.setHgrow(valueText, Priority.ALWAYS);
        final VBox container = new VBox();
        container.getChildren().addAll(nameText, valueBox, separator);
        this.setGraphic(container);
        this.setVisible(attributeValue != null);
        this.setManaged(attributeValue != null);
        this.setMaxWidth(Double.MAX_VALUE);
        this.getStyleClass().add("text-icon-button");
        valueText.setMaxWidth(Double.MAX_VALUE);
    }

    public void setOnIconPressed(EventHandler<MouseEvent> action) {
        trailing.setOnMouseClicked(action);
        trailing.setCursor(Cursor.HAND);
    }
}
