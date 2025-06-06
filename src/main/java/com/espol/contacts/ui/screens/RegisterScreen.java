package com.espol.contacts.ui.screens;

import com.espol.contacts.config.router.*;

import com.espol.contacts.ui.fragments.attributeField.SimpleFormField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.kordamp.ikonli.fontawesome6.FontAwesomeRegular.USER;
import static org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.EXCLAMATION_TRIANGLE;
import static org.kordamp.ikonli.material2.Material2OutlinedAL.EMAIL;
import static org.kordamp.ikonli.material2.Material2OutlinedAL.LOCK;

public class RegisterScreen implements Initializable {

    @FXML
    private VBox container;
    private SimpleFormField username;
    private SimpleFormField email;
    private SimpleFormField password;
    private SimpleFormField confirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username = new SimpleFormField("Nombre de usuario", USER);
        email = new SimpleFormField("Correo electrónico", EMAIL);
        password = new SimpleFormField("Crea una contraseña segura", LOCK);
        confirmPassword = new SimpleFormField("Repite la contraseña", LOCK);

        username.setValidator(text -> text == null || text.trim().isEmpty() ? "Nombre de usuario es requerido" : null);
        //email.setValidator(text -> text == null || !text.matches("[a-z]@*") ? "No es un email válido" : null);
        //password.setValidator(text -> text == null || !text.matches("[a-z]@*") ? "Contraseña poco segura" : null);
        confirmPassword.setValidator(text -> text == null || !text.equals(password.getValue()) ? "Las contraseñas no coinciden" : null);

        username.setOnAction(this::register);
        email.setOnAction(this::register);
        password.setOnAction(this::register);
        confirmPassword.setOnAction(this::register);

        container.getChildren().addAll(2, List.of(username, email, password, confirmPassword));
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
                            .graphic(new FontIcon(EXCLAMATION_TRIANGLE))
                            .show();
                    return;
                }
            }
        }

        // TODO: Handle Register
        AppRouter.setRoot(Routes.HOME);
    }
}
