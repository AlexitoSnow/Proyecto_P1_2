package com.espol.contacts;

import com.espol.contacts.config.router.AppRouter;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        AppRouter.initStage(stage);
    }
    
    public static void main(String[] args) {
        launch();
    }

}