package com.cos201.coursemanager.utils;

import com.cos201.coursemanager.constants.AppInfos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Optional;

/**
 * Utility class for displaying dialogs throughout the application.
 */
public final class Dialogue {

    private Dialogue() {
    }

    /**
     * Displays an error dialog.
     *
     * @param message the error message
     */
    public static void error(String message) {
        show(Alert.AlertType.ERROR, "Error", message);
    }

    /**
     * Displays an error dialog and logs the exception.
     *
     * @param message the error message
     * @param exception the exception to log
     */
    public static void error(String message, Exception exception) {
        exception.printStackTrace();
        show(Alert.AlertType.ERROR, "Error", message);
    }

    /**
     * Displays a success dialog.
     *
     * @param message the success message
     */
    public static void success(String message) {
        show(Alert.AlertType.INFORMATION, "Success", message);
    }

    /**
     * Displays an information dialog.
     *
     * @param message the information message
     */
    public static void info(String message) {
        show(Alert.AlertType.INFORMATION, "Information", message);
    }

    /**
     * Displays a warning dialog.
     *
     * @param message the warning message
     */
    public static void warning(String message) {
        show(Alert.AlertType.WARNING, "Warning", message);
    }

    /**
     * Displays a confirmation dialog.
     *
     * @param message the confirmation message
     * @return true if user clicked OK, false otherwise
     */
    public static boolean confirm(String title, String headerText, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title != null ? title : "Confirmation");
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        InputStream iconStream =
                Dialogue.class.getResourceAsStream(AppInfos.ICON);

        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }
        alert.setResizable(false);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Internal helper method.
     */
    private static void show(Alert.AlertType type,
                             String title,
                             String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        InputStream iconStream =
                Dialogue.class.getResourceAsStream(AppInfos.ICON);

        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }
        alert.setResizable(false);

        alert.showAndWait();
    }

}