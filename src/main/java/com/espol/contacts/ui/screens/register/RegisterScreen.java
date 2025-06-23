package com.espol.contacts.ui.screens.register;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.domain.entity.User;
import com.espol.contacts.domain.repository.UsersRepository;
import com.espol.contacts.infrastructure.repository.UsersRepositoryImpl;
import com.espol.contacts.ui.fragments.attributeField.SimpleFormField;
import com.espol.contacts.ui.screens.Initializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Map;

public class RegisterScreen implements Initializer {

    @FXML
    private VBox container;
    private SimpleFormField username;
    private SimpleFormField password;
    private SimpleFormField confirmPassword;
    private final UsersRepository repository;

    public RegisterScreen() {
        repository = UsersRepositoryImpl.getInstance();
    }

    @Override
    public void initialize(Map<String, Object> params) {
        username = new SimpleFormField("Nombre de usuario", Icons.R_USER);
        password = new SimpleFormField("Crea una contraseña segura", Icons.LOCK);
        confirmPassword = new SimpleFormField("Repite la contraseña", Icons.LOCK);

        username.setValidator(text -> text == null || text.trim().isEmpty() ? "Nombre de usuario es requerido" : null);
        confirmPassword.setValidator(text -> text == null || !text.equals(password.getValue()) ? "Las contraseñas no coinciden" : null);

        username.setOnAction(this::register);
        password.setOnAction(this::register);
        confirmPassword.setOnAction(this::register);

        container.getChildren().add(2, username);
        container.getChildren().add(3, password);
        container.getChildren().add(4, confirmPassword);
    }

    @FXML
    void goToLogin(ActionEvent event) {
        AppRouter.setRoot(Routes.LOGIN);
    }

    @FXML
    void register(ActionEvent event) {
        for (Node node : container.getChildren()) {
            if (node instanceof SimpleFormField) {
                final String result = ((SimpleFormField) node).validate();
                if (result != null) {
                    Notifications.create()
                            .title("Error de validación")
                            .text(result)
                            .graphic(new FontIcon(Icons.EXCLAMATION_TRIANGLE))
                            .show();
                    return;
                }
            }
        }

        String usernameValue = username.getValue();
        String passwordValue = password.getValue();
        User user = new User(usernameValue, passwordValue);
        if (repository.register(user)) {
            Notifications.create()
                    .title("Registro exitoso")
                    .text("Usuario registrado correctamente")
                    .graphic(new FontIcon(Icons.R_USER))
                    .show();
            SessionManager.getInstance().setCurrentUser(user);
            AppRouter.setRoot(Routes.HOME);
        } else {
            Notifications.create()
                    .title("Error de registro")
                    .text("El nombre de usuario ya están en uso")
                    .graphic(new FontIcon(Icons.EXCLAMATION_TRIANGLE))
                    .show();
            username.setValue(null);
            password.setValue(null);
            confirmPassword.setValue(null);
            username.requestFocus();
        }
    }
}
