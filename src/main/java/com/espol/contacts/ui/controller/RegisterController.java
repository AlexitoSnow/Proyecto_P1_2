package com.espol.contacts.ui.controller;

import com.espol.contacts.config.router.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void goToLogin(ActionEvent event) {
        AppRouter.setRoot(Routes.LOGIN);
    }

    @FXML
    void register(ActionEvent event) {
        AppRouter.setRoot(Routes.HOME);
    }
}
