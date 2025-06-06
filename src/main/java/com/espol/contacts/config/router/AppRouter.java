package com.espol.contacts.config.router;

import com.espol.contacts.App;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.enums.ContactType;
import com.espol.contacts.ui.screens.RegisterContactScreen;
import javafx.fxml.FXMLLoader;
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
            e.printStackTrace();
        }
    }

    /**
     * Navigate to another screen with the given route and a param
     * @param route to navigate
     * @param data to send it to the route
     */
    public static <T> void setRoot(String route, T data) {
        try {
            FXMLLoader loader = loadFXML(route);

            loader.setControllerFactory(type -> {
                try {
                    Object controllerInstance = type.getDeclaredConstructor().newInstance();
                    if (route.equals(Routes.REGISTER_CONTACT)) {
                        if (data instanceof ContactType) ((RegisterContactScreen) controllerInstance).setContactType((ContactType) data);
                        if (data instanceof Contact) ((RegisterContactScreen) controllerInstance).setContact((Contact) data);
                    }
                    return controllerInstance;
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al crear la instancia del controlador para " + type.getName(), e);
                    throw new RuntimeException("No se pudo crear la instancia del controlador.", e);
                }
            });

            scene.setRoot(loader.load());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al cargar FXML o establecer la raíz para " + route, e);
        }
    }
    
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/screens/" + fxml + ".fxml"));
        log.log(Level.INFO, "Navigate to {0}", fxml);
        return fxmlLoader;
    }
}
