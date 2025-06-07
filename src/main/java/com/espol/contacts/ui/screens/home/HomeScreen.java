package com.espol.contacts.ui.screens.home;

import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.router.*;
import com.espol.contacts.config.utils.list.ArrayList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.entity.*;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.infrastructure.repository.UsersRepositoryImpl;
import com.espol.contacts.ui.screens.home.fragments.ContactCell;
import com.espol.contacts.ui.screens.home.fragments.ContactView;
import com.espol.contacts.ui.fragments.ExtraInfoView;
import com.espol.contacts.ui.screens.home.fragments.EmptyLabel;
import com.espol.contacts.ui.screens.home.fragments.SearchField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class HomeScreen implements Initializable, Observer<Contact> {
    private static final Logger LOGGER = Logger.getLogger(HomeScreen.class.getName());
    @FXML
    public MenuButton createButton;
    @FXML
    private VBox contactsListView;
    @FXML
    private ChoiceBox<String> showButton;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Text countLabel;
    @FXML
    private VBox navigationRow;
    @FXML
    private MenuButton sortButton;
    @FXML
    private SearchField searchField;

    private final ContactsRepository repository;
    private List<Contact> allContactsData;
    private Predicate<Contact> currentFilterPredicate;
    private Predicate<Contact> currentSearchPredicate;
    private Comparator<Contact> currentSortComparator;
    private Integer selectedIndex;
    private ContactCell selectedCell;

    public HomeScreen() {
        this.repository = ContactsRepositoryImpl.getInstance();
        allContactsData = repository.getAll();
        repository.addObserver(this);
        currentFilterPredicate = c -> true;
        currentSearchPredicate = c -> true;
        currentSortComparator = Constants.COMPARATORS.get("Nombre");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigationRow.setVisible(false);
        navigationRow.setManaged(false);

        searchField.setOnSearch((predicate) -> {
            currentSearchPredicate = predicate;
            displayContacts(true);
        });

        displayContacts(true);

        mainPane.setCenter(new EmptyLabel());

        createButton.getItems().forEach(item -> item.setOnAction(e -> {
            repository.removeObserver(this);
            AppRouter.setRoot(Routes.FORM, ContactType.valueOf(item.getText()));
        }));

        showButton.getItems().addAll("Todos", "Favoritos", "Personas", "Empresas");
        showButton.setValue("Todos");
        showButton.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onApplyFilter(newValue);
        });

        Constants.COMPARATORS.forEach(
                (name, comparator) -> {
                    MenuItem menuItem = new MenuItem(name);
                    menuItem.setOnAction(e -> {
                        currentSortComparator = comparator;
                        sortButton.setText("Ordenar por: " + name);
                        displayContacts(true);
                    });
                    sortButton.getItems().add(menuItem);
                }
        );

        sortButton.setText("Ordenar por: Nombre");

    }

    private void displayContacts(boolean clearSelection) {
        Platform.runLater(() -> {
            contactsListView.getChildren().clear();


            List<Contact> filtered = new ArrayList<>();
            for (Contact contact : allContactsData) {
                if (currentFilterPredicate.test(contact) && currentSearchPredicate.test(contact)) {
                    filtered.addLast(contact);
                }
            }

            if (currentSortComparator != null) {
                filtered.sort(currentSortComparator);
            }

            if (!filtered.isEmpty()) {
                filtered.forEach(contact -> {
                    ContactCell contactCell = new ContactCell(contact);
                    contactCell.setOnMouseClicked(event -> onContactSelected(contactCell));
                    contactsListView.getChildren().add(contactCell);
                });
            }

            if (clearSelection) {
                selectedIndex = null;
                selectedCell = null;
                mainPane.setCenter(new EmptyLabel());
                mainPane.setRight(null);
            } else {
                if (selectedIndex != null && selectedIndex < contactsListView.getChildren().size()) {
                    ContactCell contactCell = (ContactCell) contactsListView.getChildren().get(selectedIndex);
                    onContactSelected(contactCell);
                } else {
                    mainPane.setCenter(new EmptyLabel());
                    mainPane.setRight(null);
                }
            }
            updateCount();
        });
    }

    private void onContactSelected(ContactCell contactCell) {
        final Contact contact = contactCell.getContact();
        if (selectedCell != null) selectedCell.setSelected(false);

        selectedIndex = contactsListView.getChildren().indexOf(contactCell);
        selectedCell = contactCell;
        selectedCell.setSelected(true);

        ContactView contactView;
        ExtraInfoView rightPane = new ExtraInfoView(contact, false);
        if (contact.getContactType() == ContactType.Persona) {
            contactView = ContactView.builder().personBuilder((Person) contact).build();
        } else {
            contactView = ContactView.builder().companyBuilder((Company) contact).build();
        }
        ScrollPane centerPane = new ScrollPane();
        centerPane.setFitToHeight(true);
        centerPane.setFitToWidth(true);
        centerPane.setContent(contactView);
        Platform.runLater(() -> {
            mainPane.setCenter(centerPane);
            mainPane.setRight(rightPane);
            navigationRow.setVisible(true);
            navigationRow.setManaged(true);
            final String text = String.format("Contacto %d de %d", selectedIndex + 1, contactsListView.getChildren().size());
            countLabel.setText(text);
        });
        LOGGER.info("Selected Index: " + selectedIndex);
    }

    void onApplyFilter(String text) {
        if (text.equals("Todos")) {
            currentFilterPredicate = c -> true;
        } else if (text.equals("Favoritos")) {
            currentFilterPredicate = Contact::isFavorite;
        } else {
            currentFilterPredicate = c -> c.getContactType() == ContactType.valueOf(text.substring(0, text.length() - 1));
        }
        displayContacts(true);
        Platform.runLater(this::updateCount);
    }

    private void updateCount() {
        final String text = String.format("Mostrando %d de %d contactos.", contactsListView.getChildren().size(), allContactsData.size());
        countLabel.setText(text);
    }

    @FXML
    void goNext(ActionEvent event) {
        Platform.runLater(() -> {
            ((ContactCell) contactsListView.getChildren().get(selectedIndex)).setSelected(false);
            if (selectedIndex == contactsListView.getChildren().size() - 1) {
                selectedIndex = 0;
                ContactCell contactCell = (ContactCell) contactsListView.getChildren().get(selectedIndex);
                onContactSelected(contactCell);
            } else {
                ContactCell contactCell = (ContactCell) contactsListView.getChildren().get(++selectedIndex);
                onContactSelected(contactCell);
            }
        });
    }

    @FXML
    void goPrevious(ActionEvent event) {
        Platform.runLater(() -> {
            if (selectedIndex == 0) {
                selectedIndex = contactsListView.getChildren().size() - 1;
                ContactCell contactCell = (ContactCell) contactsListView.getChildren().get(selectedIndex);
                onContactSelected(contactCell);
            } else {
                ContactCell contactCell = (ContactCell) contactsListView.getChildren().get(--selectedIndex);
                onContactSelected(contactCell);
            }
        });
    }

    @FXML
    void logOut(ActionEvent event) {
        repository.removeObserver(this);
        UsersRepositoryImpl.getInstance().logout();
        AppRouter.setRoot(Routes.LOGIN);
    }

    @Override
    public void update(Contact contact) {
        LOGGER.info("ContactsRepository updated, refreshing contacts list.");
        Platform.runLater(() -> {
            allContactsData = repository.getAll();
            displayContacts(contact == null);
        });
    }
}
