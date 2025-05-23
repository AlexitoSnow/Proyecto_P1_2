package com.espol.contacts.ui.controller;

import com.espol.contacts.domain.entity.Company;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.ui.fragments.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeController implements Initializable {
    private static final Logger log = Logger.getLogger(HomeController.class.getName());
    @FXML
    public MenuButton createButton;
    @FXML
    private ListView<Contact> contactsListView;
    @FXML
    private MenuButton showButton;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Text countLabel;
    @FXML
    private VBox leftPane;
    @FXML
    private VBox navigationRow;
    @FXML
    private Button sortButton;
    @FXML
    private Button filterButton;

    private final ContactsRepository repository;
    private final List<Contact> contacts;
    private int currentTabIndex = 0;

    public HomeController() {
        this.repository = new ContactsRepositoryImpl();
        this.contacts = repository.getAll();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigationRow.setVisible(false);
        navigationRow.setManaged(false);
        Platform.runLater(() -> {
            Scene scene = mainPane.getScene();
            Stage stage = (Stage) scene.getWindow();
            if (stage == null) return;
            scene.widthProperty().addListener((o, old, newValue) -> {
                leftPane.setMinWidth(newValue.doubleValue() * 0.3);
            });
            stage.maximizedProperty().addListener((o, old, newValue) -> {
                leftPane.setMinWidth(scene.getWidth() * 0.3);
            });
            leftPane.setMinWidth(scene.getWidth() * 0.3);
        });
        contactsListView.maxWidthProperty().bind(leftPane.widthProperty());

        final ObservableList<Contact> contactList = FXCollections.observableArrayList(contacts);
        contactsListView.setItems(contactList);
        contactsListView.setCellFactory(contacts -> new ContactCell(repository));
        contactsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            ContactView contactView;
            ContactActionView contactActionView = new ContactActionView(newValue);
            if (newValue.getContactType() == ContactType.Persona) {
                contactView = ContactView.builder().personBuilder((Person) newValue).build();
            } else {
                contactView = ContactView.builder().companyBuilder((Company) newValue).build();
            }
            ScrollPane centerPane = new ScrollPane();
            centerPane.setFitToHeight(true);
            centerPane.setFitToWidth(true);
            centerPane.setContent(contactView);
            ScrollPane rightPane = new ScrollPane();
            rightPane.setFitToHeight(true);
            rightPane.setFitToWidth(true);
            rightPane.setContent(contactActionView);
            Platform.runLater(() -> {
                mainPane.setCenter(centerPane);
                mainPane.setRight(rightPane);
                navigationRow.setVisible(true);
                navigationRow.setManaged(true);
                final String text = String.format("Contacto %d de %d", contactsListView.getSelectionModel().getSelectedIndex() + 1, contactsListView.getItems().size());
                countLabel.setText(text);
            });
            log.info("Selected Index: " + contactsListView.getSelectionModel().getSelectedIndex());
        });
        mainPane.setCenter(new EmptyLabel());
        createButton.getItems().forEach(item -> item.setOnAction(e -> onCreate(ContactType.valueOf(item.getText()))));
        showButton.getItems().forEach(item -> item.setOnAction(e -> onApplyFilter(item.getText(), ((FontIcon)item.getGraphic()).getIconCode())));
    }

    void onCreate(ContactType contactType) {
        mainPane.setCenter(new ContactForm());
    }

    void onApplyFilter(String text, Ikon icon) {
        contactsListView.getItems().clear();
        var list = contacts;
        if (text.equals("Todos")) {
            list = repository.getAll();
            currentTabIndex = 0;
        } else if (text.equals("Favoritos")) {
            list = repository.getFavorites();
            currentTabIndex = 1;
        } else {
            list = repository.getByType(ContactType.valueOf(text.substring(0, text.length() - 1)));
        }
        contactsListView.setItems(FXCollections.observableArrayList(list));
        filterButton.setText(text);
        filterButton.setGraphic(new FontIcon(icon));
        updateCount();
    }

    private void updateCount() {
        final String text = String.format("Contacto %d de %d.", contactsListView.getSelectionModel().getSelectedIndex() + 1, contactsListView.getItems().size());
        countLabel.setText(text);
    }

    @FXML
    void goNext(ActionEvent event) {
        final int selected = contactsListView.getSelectionModel().getSelectedIndex();
        if (selected == contactsListView.getItems().size() - 1) {
            contactsListView.getSelectionModel().selectFirst();
        } else {
            contactsListView.getSelectionModel().selectNext();
        }
    }

    @FXML
    void goPrevious(ActionEvent event) {
        final int selected = contactsListView.getSelectionModel().getSelectedIndex();
        if (selected == 0) {
            contactsListView.getSelectionModel().selectLast();
        } else {
            contactsListView.getSelectionModel().selectPrevious();
        }
    }

    @FXML
    void onFilterMenuRequested(MouseEvent event) {
        FilterMenu menu = new FilterMenu();
        menu.show(filterButton, event.getScreenX(), event.getScreenY());
    }

    @FXML
    void onSortMenuRequested(MouseEvent event) {
        SortMenu menu = new SortMenu();
        menu.show(sortButton, event.getScreenX(), event.getScreenY());
    }
    
}
