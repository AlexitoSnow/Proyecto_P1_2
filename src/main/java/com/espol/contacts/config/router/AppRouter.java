package com.espol.contacts.config.router;

import com.espol.contacts.App;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.ui.controller.DataInitializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppRouter {
    private static final Logger log = Logger.getLogger(AppRouter.class.getName());

    private static Scene scene;
    private static final double WIDTH = 640;
    private static final double HEIGHT = 480;
    
    private AppRouter() {}
    
    /**
     * Launch the app in the parent route
     * Declare the basic screen configuration that will be applied to the stage
     * @param stage to show the scenes
     */
    public static void initStage(Stage stage) {
        try {
            scene = new Scene(loadFXML(Routes.LOGIN).load(), WIDTH, HEIGHT);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load main view fxml\nMessage: {0}", e.getMessage());
        }
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle(Constants.APP_NAME);
        stage.show();
    }
    
    /**
     * Navigate to another screen with the given route
     * @param route to navigate
     */
    public static void setRoot(String route) {
        try {
            scene.setRoot(loadFXML(route).load());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load FXML", e.getMessage());
        }
    }

    /**
     * Navigate to another screen with the given route
     * @param route to navigate
     */
    public static <T> void setRoot(String route, T data) {
        try {
            FXMLLoader loader = loadFXML(route);
            Parent parent = loader.load();
            Object controller = loader.getController();
            if (controller instanceof DataInitializable) {
                ((DataInitializable<T>) controller).initData(data);
            }

            scene.setRoot(parent);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load FXML", e.getMessage());
        }
    }
    
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/screens/" + fxml + ".fxml"));
        log.log(Level.INFO, "Navigate to {0}", fxml);
        return fxmlLoader;
    }
}
