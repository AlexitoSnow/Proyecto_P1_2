package com.espol.contacts.ui.fragments;

import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.fontawesome6.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.logging.Logger;

import static org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.USER_CIRCLE;
import static org.kordamp.ikonli.material2.Material2AL.ARROW_FORWARD_IOS;

// TODO: Implementar
public class ContactActionView extends HBox {
    private final Logger logger = Logger.getLogger(ContactActionView.class.getName());
    private final Contact contact;
    private final ContactsRepository repository;
    private final ToggleButton favoriteButton;
    private final Button editButton;
    private final Button deleteButton;

    public ContactActionView(Contact contact) {
        this.contact = contact;
        repository = new ContactsRepositoryImpl();

        favoriteButton = new ToggleButton("Favorito");
        favoriteButton.getStyleClass().add("favorite-button");
        favoriteButton.setSelected(contact.isFavorite());
        favoriteButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            logger.info("Favorito: " + newValue);
            favoriteButton.setGraphic(new FontIcon(
                    newValue ? FontAwesomeSolid.STAR : FontAwesomeRegular.STAR
            ));
        });
        favoriteButton.setGraphic(new FontIcon(
                contact.isFavorite() ? FontAwesomeSolid.STAR : FontAwesomeRegular.STAR
        ));

        editButton = new Button("Editar", new FontIcon(FontAwesomeSolid.EDIT));
        deleteButton = new Button("Eliminar", new FontIcon(FontAwesomeSolid.TRASH));
        editButton.getStyleClass().add("text-icon-button");
        deleteButton.getStyleClass().add("text-icon-button");

        favoriteButton.setOnAction(e -> toggleFavorite());
        editButton.setOnAction(e -> toggleEdit());
        deleteButton.setOnAction(e -> deleteContact());

        final VBox vbox = new VBox();
        final Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefWidth(1);
        separator.setMaxHeight(Double.MAX_VALUE);
        final FontIcon icon = new FontIcon(USER_CIRCLE);
        icon.setIconSize(72);
        icon.setTextAlignment(TextAlignment.CENTER);
        final ButtonBar bar = new ButtonBar();
        bar.getButtons().addAll(favoriteButton, editButton, deleteButton);
        vbox.getChildren().addAll(icon, bar, new Separator(), getGallery(), getRelatedContacts());
        vbox.setSpacing(8);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(0, 8, 0, 8));
        this.getChildren().addAll(separator, vbox);
    }

    private void toggleFavorite() {
        contact.setFavorite(!contact.isFavorite());
        repository.save(contact);
    }

    private void toggleEdit() {

    }

    private void deleteContact() {
        repository.delete(contact);
        this.getChildren().clear();
    }

    private VBox getGallery() {
        final VBox gallery = new VBox();
        final HBox rowImages = new HBox(4);
        final HBox rowTitle = new HBox();
        final Label titleText = new Label("Galería");
        final Button viewMoreButton = new Button("Ver más", new FontIcon(ARROW_FORWARD_IOS));
        viewMoreButton.setContentDisplay(ContentDisplay.RIGHT);
        viewMoreButton.getStyleClass().add("text-icon-button");
        titleText.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(titleText, Priority.ALWAYS);
        rowTitle.getChildren().addAll(titleText, viewMoreButton);
        rowImages.getChildren().addAll(new Rectangle(100, 100), new Rectangle(100, 100));
        gallery.getChildren().addAll(rowTitle, rowImages);
        return gallery;
    }

    private VBox getRelatedContacts() {
        final VBox relatedContacts = new VBox();
        final Label titleText = new Label("Contactos Relacionados");
        var box = new RelatedContactCell(Person.builder().name("Persona 1").build());
        var box2 = new RelatedContactCell(Person.builder().name("Persona 2").build());
        relatedContacts.getChildren().addAll(titleText, box, box2);
        return relatedContacts;
    }
}
