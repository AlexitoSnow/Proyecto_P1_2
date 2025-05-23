package com.espol.contacts.ui.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.RadioMenuItem;

// TODO: Implement
public class SortMenu extends ContextMenu {
    public SortMenu() {
        var nameSort = new RadioMenuItem("Nombre");
        var lastNameSort = new RadioMenuItem("Apellido");
        var typeSort = new RadioMenuItem("Tipo");
        this.getItems().addAll(nameSort, lastNameSort, typeSort);
    }
}
