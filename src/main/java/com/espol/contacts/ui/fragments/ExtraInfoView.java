package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.SessionManager;
import com.espol.contacts.config.constants.Constants;
import com.espol.contacts.config.constants.Icons;
import com.espol.contacts.config.router.AppRouter;
import com.espol.contacts.config.router.Routes;
import com.espol.contacts.config.utils.list.CircularDoublyLinkedList;
import com.espol.contacts.domain.entity.Contact;
import com.espol.contacts.domain.entity.RelatedContact;
import com.espol.contacts.domain.repository.ContactsRepository;
import com.espol.contacts.infrastructure.repository.ContactsRepositoryImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ExtraInfoView extends ScrollPane {
    @FXML
    private FontIcon favoriteIcon;
    @FXML
    private Button galleryButton;
    @FXML
    private FlowPane imageGallery;
    @FXML
    private HBox quickActionsToolbar;
    @FXML
    private VBox relatedContacts;
    @FXML
    private Button relatedContactsButton;
    @FXML
    private TitledPane galleryTitlePane;
    @FXML
    private TitledPane relatedTitlePane;
    @FXML
    private ToggleButton favoriteButton;
    @FXML
    private VBox mainPane;

    private ProfilePicture profilePicture;
    private Contact contact;
    private ContactsRepository repository;
    private boolean isEditable;

    private static final Logger LOGGER = Logger.getLogger(ExtraInfoView.class.getName());

    public ExtraInfoView(Contact contact, boolean isEditable) {
        this.contact = contact;
        this.isEditable = isEditable;
        this.repository = ContactsRepositoryImpl.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExtraInfoView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("No se pudo cargar el FXML para ExtraInfoView", exception);
        }
    }

    @FXML
    void initialize() {
        profilePicture = new ProfilePicture(null, 100, isEditable);
        mainPane.getChildren().add(0, profilePicture);
        if (isEditable) { // Modo edición

            quickActionsToolbar.setVisible(false);
            quickActionsToolbar.setManaged(false);

            galleryButton.setVisible(true);
            galleryButton.setManaged(true);
            relatedContactsButton.setVisible(true);
            relatedContactsButton.setManaged(true);

        } else { // Modo vista

            quickActionsToolbar.setVisible(true);
            quickActionsToolbar.setManaged(true);

            galleryButton.setVisible(false);
            galleryButton.setManaged(false);
            relatedContactsButton.setVisible(false);
            relatedContactsButton.setManaged(false);

            favoriteIcon.setIconLiteral(contact.isFavorite() ? "fas-star" : "far-star");
            favoriteButton.setSelected(contact.isFavorite());

            galleryTitlePane.setExpanded(!contact.getGallery().isEmpty());
            relatedTitlePane.setExpanded(!contact.getRelatedContacts().isEmpty());
        }

        setProfilePicture();
        setGallery();
        setRelatedContacts();
    }

    @FXML
    void onAddGallery(ActionEvent event) {
        galleryTitlePane.setExpanded(true);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Agregar imagen a la galería");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        var file = chooser.showOpenDialog(this.getScene().getWindow());
        if (file != null) {
            ImageGallery imageGalleryItem = new ImageGallery(file.getAbsolutePath(), isEditable);
            imageGallery.getChildren().add(imageGalleryItem);
            if (isEditable) {
                imageGalleryItem.setOnRemove(e -> {
                    imageGallery.getChildren().remove(imageGalleryItem);
                    if (imageGallery.getChildren().isEmpty()) {
                        galleryTitlePane.setExpanded(false);
                    }
                });
            }
        }
        galleryTitlePane.setExpanded(!imageGallery.getChildren().isEmpty());
    }

    @FXML
    void onAddRelated(ActionEvent event) {
        RelatedContactBox relatedContactBox = new RelatedContactBox(isEditable, contact);
        relatedContactBox.setOnRemoveAction(e -> {
            relatedContacts.getChildren().remove(relatedContactBox);
        });
        relatedContacts.getChildren().add(relatedContactBox);
        relatedTitlePane.setExpanded(true);
    }

    @FXML
    void onToggleFavorite(ActionEvent event) {
        contact.setFavorite(!contact.isFavorite());
        favoriteIcon.setIconLiteral(contact.isFavorite() ? Icons.S_STAR :Icons.REGULAR_STAR);
        repository.save(contact);
    }

    @FXML
    void goToEdit(ActionEvent event) {
        AppRouter.setRoot(Routes.FORM, contact);
    }

    @FXML
    void onDelete(ActionEvent event) {
        repository.delete(contact);
    }

    public Set<String> getGalleryImages(String contactName) {
        String path = Constants.GALLERY_FOLDER +
                File.separator +
                SessionManager.getInstance().getCurrentUser().getUsername() +
                File.separator +
                contactName +
                File.separator;

        Set<String> images = imageGallery.getChildren().stream().map(node -> {
            final ImageGallery imageGallery = (ImageGallery) node;
            String imagePath = imageGallery.getImage();
            if (imagePath.contains("app/gallery")) {
                // If the image is already in the gallery, return it
                return imagePath;
            } else {
                // Otherwise, copy it to the gallery folder
                String copiedPath = copyTo(imagePath, path, null);
                if (copiedPath != null) {
                    return copiedPath;
                }
            }
            return imagePath;
        }).collect(Collectors.toSet());

        File directory = new File(path);
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                String fileName = file.getName();
                boolean existsInGallery = images.stream().anyMatch(imgPath -> imgPath.endsWith(fileName));
                if (!existsInGallery) {
                    file.delete();
                }
            }
        }
        return images;
    }

    public Set<RelatedContact> getRelatedContacts() {
        return relatedContacts.getChildren().stream()
                .map(node -> ((RelatedContactBox) node).getValue())
                .filter(rc -> rc != null)
                .collect(Collectors.toSet());
    }

    public void setProfilePicture() {
        if (contact == null) return;
        if (contact.getProfilePicture() != null) {
            profilePicture.setImage(contact.getProfilePicture());
        }
    }

    public String getProfilePicture(String contactName) {
        String path = Constants.GALLERY_FOLDER +
                File.separator +
                SessionManager.getInstance().getCurrentUser().getUsername() +
                File.separator +
                contactName +
                File.separator + "profile" + File.separator;
        String profilePath = profilePicture.getImage();
        if (profilePath != null) {
            if (profilePath.contains("app/gallery")) {
                // If the image is already in the gallery, return it
                return profilePath;
            } else {
                // Otherwise, copy it to the gallery folder
                String copiedPath = copyTo(profilePath, path, "profile");
                if (copiedPath != null) {
                    return copiedPath;
                }
            }
        }
        return null;
    }

    private void setGallery() {
        if (isEditable) {
            if (contact == null) return;
            contact.getGallery().forEach(
                    path -> {
                        if (!new File(path).exists()) return;
                        ImageGallery imageGalleryItem = new ImageGallery(path, true);
                        imageGalleryItem.setOnRemove(e -> {
                            imageGallery.getChildren().remove(imageGalleryItem);
                            if (imageGallery.getChildren().isEmpty()) {
                                galleryTitlePane.setExpanded(false);
                            }
                        });
                        imageGallery.getChildren().add(imageGalleryItem);
                    }
            );
        } else {
            contact.getGallery().forEach(
                    path -> {
                        if (!new File(path).exists()) return;
                        ImageGallery imageGalleryItem = new ImageGallery(path, false);
                        imageGalleryItem.setOnShow(e -> {
                            final CircularDoublyLinkedList<String> imagePaths = new CircularDoublyLinkedList<>();
                            imageGallery.getChildren().forEach(node -> {
                                if (node instanceof ImageGallery) {
                                    ImageGallery galleryItem = (ImageGallery) node;
                                    String imagePath = galleryItem.getImage();
                                    imagePaths.addLast(imagePath);
                                }
                            });
                            AppRouter.openNewWindow(Routes.EXPLORER, "Explorador de Imágenes", Map.of("list", imagePaths, "index", imageGallery.getChildren().indexOf(imageGalleryItem)));
                        });
                        imageGallery.getChildren().add(imageGalleryItem);
                    }
            );
        }
    }

    private void setRelatedContacts() {
        if (isEditable) {
            if (contact == null) return;
            contact.getRelatedContacts().forEach(
                    relatedContact -> {
                        RelatedContactBox relatedContactBox = new RelatedContactBox(true, contact);
                        relatedContactBox.setValue(relatedContact);
                        relatedContactBox.setOnRemoveAction(e -> {
                            relatedContacts.getChildren().remove(relatedContactBox);
                        });
                        relatedContacts.getChildren().add(relatedContactBox);
                    }
            );
        } else {
            contact.getRelatedContacts().forEach(
                    relatedContact -> {
                        RelatedContactBox relatedContactBox = new RelatedContactBox(false, contact);
                        relatedContactBox.setValue(relatedContact);
                        relatedContacts.getChildren().add(relatedContactBox);
                    }
            );
        }
    }

    private String copyTo(String currentFile, String destinationFolder, String newName) {
        currentFile = URLDecoder.decode(currentFile, StandardCharsets.UTF_8);
        try {
            String fileName = currentFile.substring(currentFile.lastIndexOf("/") + 1);
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String finalName = (newName != null) ? newName + extension : fileName;
            File sourceFile = new File(currentFile);
            File destinationFile = new File(destinationFolder + File.separator + finalName);
            destinationFile.getParentFile().mkdirs();

            LOGGER.info("Copying image from: " + sourceFile.getAbsolutePath() + "\nto: " + destinationFile.getAbsolutePath());
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return destinationFile.getAbsolutePath();
        } catch (java.io.IOException e) {
            LOGGER.severe("Error copying image: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
