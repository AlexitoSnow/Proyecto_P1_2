package com.espol.contacts.config.router;

import com.espol.contacts.App;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppRouter {
    private static Logger log = Logger.getLogger(AppRouter.class.getName());

    private static Scene scene;
    private static final double WIDTH = 640;
    private static final double HEIGTH = 480;
    
    private AppRouter() {}
    
    /**
     * Launch the app in the parent route
     * 
     * Declare the basic screen configuration that will be applied to the stage
     * @param stage to show the scenes
     * @throws IOException
     */
    public static void initStage(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Routes.LOGIN), WIDTH, HEIGTH);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Navigate to another screen with the given route
     * @param route to navigate
     * @throws IOException
     */
    public static void setRoot(String route) throws IOException {
        scene.setRoot(loadFXML(route));
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("screens/" + fxml + ".fxml"));
        log.log(Level.INFO, "Navigate to {0}", fxml);
        return fxmlLoader.load();
    }
}
