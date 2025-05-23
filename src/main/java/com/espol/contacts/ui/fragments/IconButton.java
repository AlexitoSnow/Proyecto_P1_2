package com.espol.contacts.ui.fragments;

import javafx.scene.control.Button;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconButton extends Button {
    private final FontIcon fontIcon;

    public IconButton(Ikon icon, Integer iconSize) {
        fontIcon = new FontIcon(icon);
        fontIcon.setIconSize(iconSize);

        this.setGraphic(fontIcon);
        this.getStyleClass().add("text-icon-button");
    }

    public void setIcon(Ikon icon) {
        this.fontIcon.setIconCode(icon);
    }

    public void setIconSize(Integer iconSize) {
        this.fontIcon.setIconSize(iconSize);
    }

}
