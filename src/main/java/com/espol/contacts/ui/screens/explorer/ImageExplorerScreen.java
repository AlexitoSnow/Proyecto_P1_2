package com.espol.contacts.ui.screens.explorer;

import com.espol.contacts.config.utils.list.CircularDoublyLinkedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageExplorerScreen implements Initializable {
    @FXML
    private Text infoTxt;
    @FXML
    private ImageView image;

    private CircularDoublyLinkedList<String> imagePaths;
    private int currentIndex;

    public void setList(CircularDoublyLinkedList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            loadImage();
        } else {
            infoTxt.setText("No images available.");
        }
    }

    private void loadImage() {
        String imagePath = imagePaths.get(currentIndex);
        image.setImage(new Image("file:///" + imagePath));
        infoTxt.setText("Imagen " + (currentIndex + 1) + " de " + imagePaths.size());
    }

    @FXML
    void next(MouseEvent event) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            currentIndex = (currentIndex + 1) % imagePaths.size();
            loadImage();
        }
    }

    @FXML
    void previous(MouseEvent event) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
            loadImage();
        }
    }
}
