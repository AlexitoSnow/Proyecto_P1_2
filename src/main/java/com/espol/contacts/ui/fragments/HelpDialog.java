package com.espol.contacts.ui.fragments;

import com.espol.contacts.config.constants.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class HelpDialog extends DialogPane {

    @FXML
    private TextFlow galleryInfo;

    @FXML
    private TextFlow navigationInfo;

    @FXML
    private TextFlow relatedContactsInfo;

    @FXML
    private TextFlow searchInfo;

    public HelpDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpDialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("No se pudo cargar el FXML para HelpDialog", exception);
        }

        setStyledText(searchInfo, Constants.searchInfoText);
        setStyledText(navigationInfo, Constants.navigationInfoText);
        setStyledText(relatedContactsInfo, Constants.relatedContactsInfoText);
        setStyledText(galleryInfo, Constants.galleryInfoText);

        getButtonTypes().add(ButtonType.OK);
    }

    private void setStyledText(TextFlow flow, String content) {
        flow.getChildren().clear();
        int i = 0;
        while (i < content.length()) {
            int start = content.indexOf('*', i);
            if (start < 0) {
                flow.getChildren().add(new Text(content.substring(i)));
                break;
            }
            if (start > i) {
                flow.getChildren().add(new Text(content.substring(i, start)));
            }
            int end = content.indexOf('*', start + 1);
            if (end < 0) {
                flow.getChildren().add(new Text(content.substring(start)));
                break;
            }
            Text bold = new Text(content.substring(start + 1, end));
            bold.setStyle("-fx-font-weight: bold;");
            flow.getChildren().add(bold);
            i = end + 1;
        }
    }
}
