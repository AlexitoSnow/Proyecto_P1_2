package com.espol.contacts.ui.fragments.attributeField;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class AttributeViewField extends HBox {

    private final FontIcon trailing;

    public AttributeViewField(String attributeName, String attributeValue, String trailingIcon) {
        final Label nameText = new Label(attributeName);
        final Label valueText = new Label(attributeValue);

        nameText.setStyle("-fx-font-weight: bold;");
        valueText.setWrapText(true);
        valueText.setTooltip(new Tooltip(attributeValue));

        trailing = trailingIcon == null ? null : new FontIcon(trailingIcon);

        HBox.setHgrow(valueText, Priority.ALWAYS);
        final VBox container = new VBox(8);
        container.getChildren().addAll(nameText, valueText);
        this.setVisible(attributeValue != null);
        this.setManaged(attributeValue != null);
        this.setMaxWidth(Double.MAX_VALUE);
        valueText.setMaxWidth(Double.MAX_VALUE);

        this.getChildren().add(container);
        if (trailing != null) {
            trailing.setIconSize(30);
            this.getChildren().add(trailing);
        }

        HBox.setHgrow(container, Priority.ALWAYS);
    }

    public void setOnIconPressed(EventHandler<MouseEvent> action) {
        trailing.setOnMouseClicked(action);
        trailing.setCursor(Cursor.HAND);
    }
}
