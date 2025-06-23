package com.espol.contacts.ui.screens.explorer;

import com.espol.contacts.config.utils.list.CircularDoublyLinkedList;
import com.espol.contacts.ui.screens.Initializer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.ListIterator;
import java.util.Map;

public class ImageExplorerScreen implements Initializer {
    @FXML
    private Text infoTxt;
    @FXML
    private ImageView image;
    @FXML
    private BorderPane root;

    private CircularDoublyLinkedList<String> imagePaths;
    private ListIterator<String> iterator;

    public void initialize(Map<String, Object> params) {
        this.imagePaths = (CircularDoublyLinkedList<String>) params.get("list");
        Integer currentIndex = Integer.parseInt(params.get("index").toString());
        image.setPreserveRatio(true);

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            image.setFitWidth(newVal.doubleValue() * 0.7);
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            image.setFitHeight(newVal.doubleValue() * 0.7);
        });

        if (imagePaths == null || imagePaths.isEmpty()) {
            if (infoTxt != null) infoTxt.setText("No images available.");
            if (image != null) image.setImage(null);
            return;
        }
        iterator = imagePaths.listIterator(currentIndex);
        loadImage(iterator.next());
    }

    private void loadImage(String imagePath) {
        Platform.runLater(() -> {
            if (imagePaths == null || imagePaths.isEmpty()) {
                infoTxt.setText("No images available.");
                image.setImage(null);
                return;
            }
            int idx = iterator.previousIndex();
            image.setImage(new Image("file:///" + imagePath));
            infoTxt.setText("Imagen " + (idx + 1) + " de " + imagePaths.size());
        });
    }

    @FXML
    void next(MouseEvent event) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            loadImage(iterator.next());
        }
    }

    @FXML
    void previous(MouseEvent event) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            loadImage(iterator.previous());
        }
    }
}
