package com.espol.contacts.ui.controller;

import com.espol.contacts.config.router.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void login(ActionEvent event) {
        /*
        Handle login
         */
        AppRouter.setRoot(Routes.HOME);
    }

    @FXML
    void signUp(ActionEvent event) {
        AppRouter.setRoot(Routes.REGISTER);
    }
}
