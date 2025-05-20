package com.espol.contacts.ui.controller;

import com.espol.contacts.domain.entity.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.ui.fragments.ContactForm;
import com.espol.contacts.ui.fragments.ContactListTile;
import com.espol.contacts.ui.fragments.EmptyLabel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    private static Logger log = Logger.getLogger(HomeController.class.getName());
    @FXML
    public MenuButton createButton;
    @FXML
    private VBox contactsListView;
    @FXML
    private MenuButton filterButton;
    @FXML
    private BorderPane mainPane;

    private final ContactsRepository repository;

    public HomeController() {
        this.repository = new ContactsRepositoryImpl();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final List<ContactListTile> contactList = repository.getAll().stream().map(
                contact -> new ContactListTile(
                        "Contact",
                        null,
                        e -> log.log(Level.INFO, "Contact"))
        ).collect(Collectors.toList());
        contactsListView.getChildren().setAll(contactList);
        mainPane.setCenter(new EmptyLabel());
        createButton.getItems().forEach(item -> {
            item.setOnAction(e -> onCreate(ContactType.valueOf(item.getText())));
        });
        filterButton.getItems().forEach(item -> {
            item.setOnAction(e -> onApplyFilter(item.getText(), ((FontIcon)item.getGraphic()).getIconCode()));
        });
    }

    void onCreate(ContactType contactType) {
        mainPane.setCenter(new ContactForm());
    }

    void onApplyFilter(String text, Ikon icon) {
        filterButton.setText(text);
        filterButton.setGraphic(new FontIcon(icon));
    }
    
}
