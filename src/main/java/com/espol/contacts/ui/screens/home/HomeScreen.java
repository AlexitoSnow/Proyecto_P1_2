package com.espol.contacts.ui.screens.home;

import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.config.utils.FilterSorterList;
import com.espol.contacts.config.utils.list.List;
import com.espol.contacts.config.utils.observer.Observer;
import com.espol.contacts.domain.entity.Company;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.Person;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import com.espol.contacts.infrastructure.repository.UsersRepositoryImpl;
import com.espol.contacts.ui.fragments.ExtraInfoView;
import com.espol.contacts.ui.screens.Initializer;
import com.espol.contacts.ui.screens.home.fragments.ContactCell;
import com.espol.contacts.ui.screens.home.fragments.ContactView;
import com.espol.contacts.ui.screens.home.fragments.EmptyLabel;
import com.espol.contacts.ui.screens.home.fragments.SearchField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class HomeScreen implements Initializer, Observer<Contact> {
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
    private ListIterator<Contact> contactIterator;

    public HomeScreen() {
        this.repository = ContactsRepositoryImpl.getInstance();
        allContactsData = repository.getAll();
        repository.addObserver(this);
        currentFilterPredicate = c -> true;
        currentSearchPredicate = c -> true;
    }

    @Override
    public void initialize(Map<String, Object> params) {
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
            AppRouter.setRoot(Routes.FORM, Map.of("type", ContactType.valueOf(item.getText())));
        }));

        showButton.getItems().addAll("Todos", "Favoritos", "Personas", "Empresas");
        showButton.setValue("Todos");
        showButton.setOnAction(event -> onApplyFilter(showButton.getValue()));

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

        sortButton.setText("Selecciona un criterio de ordenación");

        if (params != null && params.get("index") != null) {
            selectContactByIndex((Integer) params.get("index"));
        }
    }

    private void displayContacts(boolean clearSelection) {
        Platform.runLater(() -> {
            contactsListView.getChildren().clear();

            List<Contact> filtered = FilterSorterList.filterAndSort(
                    allContactsData,
                    currentFilterPredicate,
                    currentSearchPredicate,
                    currentSortComparator
            );

            if (!filtered.isEmpty()) contactIterator = filtered.listIterator();

            filtered.forEach(contact -> {
                ContactCell contactCell = new ContactCell(contact);
                contactCell.setOnMouseClicked(event -> {
                    int i = contactsListView.getChildren().indexOf(contactCell);
                    selectContactByIndex(i);
                });
                contactsListView.getChildren().add(contactCell);
            });

            if (clearSelection || contactsListView.getChildren().isEmpty()) {
                clearSelection();
            } else if (selectedIndex != null) {
                selectContactByIndex(selectedIndex);
            }
            updateCount();
        });
    }

    private void clearSelection() {
        selectedIndex = null;
        selectedCell = null;
        mainPane.setCenter(new EmptyLabel());
        mainPane.setRight(null);
        navigationRow.setVisible(false);
        navigationRow.setManaged(false);
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
            if (contactIterator != null && contactIterator.hasNext()) {
                contactIterator.next();
                int idx = contactIterator.previousIndex();
                selectContactByIndex(idx);
            }
        });
    }

    @FXML
    void goPrevious(ActionEvent event) {
        Platform.runLater(() -> {
            if (contactIterator != null && contactIterator.hasPrevious()) {
                contactIterator.previous();
                int idx = contactIterator.nextIndex();
                selectContactByIndex(idx);
            }
        });
    }

    private void selectContactByIndex(int idx) {
        Platform.runLater(() -> {
            if (selectedCell != null) selectedCell.setSelected(false);

            selectedIndex = idx;
            selectedCell = (ContactCell) contactsListView.getChildren().get(idx);
            selectedCell.setSelected(true);

            Contact contact = selectedCell.getContact();
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
            mainPane.setCenter(centerPane);
            mainPane.setRight(rightPane);
            navigationRow.setVisible(true);
            navigationRow.setManaged(true);
            final String text = String.format("Contacto %d de %d", selectedIndex + 1, contactsListView.getChildren().size());
            countLabel.setText(text);
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
