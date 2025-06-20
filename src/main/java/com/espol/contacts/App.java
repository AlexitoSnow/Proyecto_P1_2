package com.espol.contacts;

import com.espol.contacts.config.LoggerConfig;
import com.espol.contacts.config.router.AppRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AppRouter.initStage(stage);
    }

    public static void main(String[] args) {
        setUserAgentStylesheet("fluent-light.css");
        LoggerConfig.configure();

        launch();
    }

}