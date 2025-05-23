package com.espol.contacts.ui.fragments;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.kordamp.ikonli.fontawesome6.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class ContactContextMenu extends ContextMenu {
    public final MenuItem editMenuItem;
    public final MenuItem deleteMenuItem;
    public final MenuItem favoriteMenuItem;

    public ContactContextMenu() {
        editMenuItem = new MenuItem("Editar", new FontIcon(FontAwesomeSolid.EDIT));
        deleteMenuItem = new MenuItem("Eliminar", new FontIcon(FontAwesomeSolid.TRASH));
        favoriteMenuItem = new MenuItem("Agregar a Favoritos", new FontIcon(FontAwesomeSolid.STAR));

        getItems().addAll(favoriteMenuItem, editMenuItem, deleteMenuItem);
    }

    public void toggleFavorite(boolean isFavorite) {
        favoriteMenuItem.setText(isFavorite ? "Remover de Favoritos" : "Añadir a Favoritos");
        favoriteMenuItem.setGraphic(
                new FontIcon(isFavorite ? FontAwesomeRegular.STAR : FontAwesomeSolid.STAR));
    }
}
