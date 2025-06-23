package com.espol.contacts;

import com.espol.contacts.config.LoggerConfig;
import com.espol.contacts.config.router.AppRouter;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/icon.png"))));
        AppRouter.initStage(stage);
    }

    public static void main(String[] args) {
        setUserAgentStylesheet("assets/styles/contacts-app.css");
        LoggerConfig.configure();

        launch();
    }

}