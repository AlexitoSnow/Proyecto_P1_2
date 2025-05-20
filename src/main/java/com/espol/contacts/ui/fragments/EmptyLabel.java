package com.espol.contacts.ui.fragments;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class EmptyLabel extends Label {

    public EmptyLabel() {
        this.setText("No has creado ningún contacto");
        this.setAlignment(Pos.CENTER);
    }
}
