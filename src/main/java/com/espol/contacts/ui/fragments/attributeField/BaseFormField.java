package com.espol.contacts.ui.fragments.attributeField;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Function;

public abstract class BaseFormField<E> extends VBox {
    protected final String hintText;
    protected FontIcon leadingIcon;
    protected Node mainField;
    protected E value;
    protected Function<String, String> validator;

    protected static final double SPACING = 4.0;
    protected static final double CONTAINER_SPACING = 4.0;
    protected static final int ICON_SIZE = 16;

    protected BaseFormField(String hintText, Ikon icon) {
        this.hintText = hintText;
        leadingIcon = new FontIcon(icon);
        leadingIcon.setIconSize(ICON_SIZE);
        setSpacing(SPACING);
        setMaxWidth(Double.MAX_VALUE);
    }

    protected void changeIcon(Ikon icon) {
        if (leadingIcon != null) {
            leadingIcon.setIconCode(icon);
        }
    }

    public abstract void initializeField();

    public E getValue() {
        return value;
    }

    public abstract void setValue(E value);

    @Override
    public void requestFocus() {
        mainField.requestFocus();
    }

    public void setValidator(Function<String, String> validator) {
        this.validator = validator;
    }

    public abstract String validate();
}