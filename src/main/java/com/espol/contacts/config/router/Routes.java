package com.espol.contacts.config.router;

import java.io.File;

/**
 * Declare constants to navigate through the application
 * The constants must have the same name of the .fxml files
 */
public class Routes {

    private Routes(){}

    public static final String LOGIN = "login" + File.separator + "LoginScreen";
    public static final String REGISTER = "register" + File.separator + "RegisterScreen";
    public static final String HOME = "home" + File.separator + "HomeScreen";
    public static final String FORM = "form" + File.separator + "FormScreen";
}