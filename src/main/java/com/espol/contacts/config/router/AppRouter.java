package com.espol.contacts.config.router;

import com.espol.contacts.App;
import com.espol.contacts.config.constants.Constants;
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
            scene = new Scene(loadFXML(Routes.HOME), WIDTH, HEIGHT);
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
            scene.setRoot(loadFXML(route));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load FXML", e.getMessage());
        }
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/screens/" + fxml + ".fxml"));
        log.log(Level.INFO, "Navigate to {0}", fxml);
        return fxmlLoader.load();
    }
}
