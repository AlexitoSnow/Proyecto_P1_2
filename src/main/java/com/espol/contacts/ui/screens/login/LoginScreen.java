package com.espol.contacts.ui.screens.login;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.router.*;

import com.espol.contacts.domain.entity.User;
import com.espol.contacts.domain.repository.UsersRepository;
import com.espol.contacts.infrastructure.repository.UsersRepositoryImpl;
import com.espol.contacts.ui.fragments.attributeField.SimpleFormField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

import static org.kordamp.ikonli.fontawesome6.FontAwesomeRegular.USER;
import static org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.EXCLAMATION_TRIANGLE;
import static org.kordamp.ikonli.material2.Material2AL.LOCK;

public class LoginScreen implements Initializable {

    @FXML
    private VBox container;
    private SimpleFormField userField;
    private SimpleFormField passwordField;
    private final UsersRepository repository;

    public LoginScreen() {
        repository = UsersRepositoryImpl.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Function<String, String> commonValidator = text -> text == null || text.isEmpty() ? "Campo requerido" : null;

        userField = new SimpleFormField("Nombre de usuario", USER);
        passwordField = new SimpleFormField("Contraseña", LOCK);

        userField.setValidator(commonValidator);
        passwordField.setValidator(commonValidator);

        userField.setOnAction(this::login);
        passwordField.setOnAction(this::login);

        container.getChildren().add(2, userField);
        container.getChildren().add(3, passwordField);
    }

    @FXML
    void login(ActionEvent event) {
        for (Node node : container.getChildren())
            if (node instanceof SimpleFormField) {
                final String result = ((SimpleFormField) node).validate();
                if (result != null) {
                    Notifications.create()
                            .title("Error de validación")
                            .text(result)
                            .graphic(new FontIcon(EXCLAMATION_TRIANGLE))
                            .show();
                    return;
                }
            }
        String username = userField.getValue();
        String password = passwordField.getValue();
        Optional<User> user = repository.authenticate(username, password);
        if (user.isPresent()) {
            SessionManager.getInstance().setCurrentUser(user.get());
            AppRouter.setRoot(Routes.HOME);
        } else {
            Notifications.create()
                    .title("Error de inicio de sesión")
                    .text("Usuario o contraseña incorrectos")
                    .graphic(new FontIcon(EXCLAMATION_TRIANGLE))
                    .show();
            userField.setValue(null);
            passwordField.setValue(null);
            userField.requestFocus();
        }

    }

    @FXML
    void signUp(ActionEvent event) {
        AppRouter.setRoot(Routes.REGISTER);
    }
}
