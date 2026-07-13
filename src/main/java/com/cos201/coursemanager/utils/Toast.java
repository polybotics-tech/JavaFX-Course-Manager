package com.cos201.coursemanager.utils;

import com.cos201.coursemanager.constants.Styles;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Utility class for displaying toast notifications.
 */
public final class Toast {

    private static final Duration FADE_IN = Duration.millis(250);
    private static final Duration DISPLAY = Duration.seconds(3.5);
    private static final Duration FADE_OUT = Duration.millis(500);

    private Toast() {
    }

    public static void success(Stage stage, String message) {
        show(stage, message, "toast-success");
    }

    public static void error(Stage stage, String message) {
        show(stage, message, "toast-error");
    }

    public static void warning(Stage stage, String message) {
        show(stage, message, "toast-warning");
    }

    public static void info(Stage stage, String message) {
        show(stage, message, "toast-info");
    }

    private static void show(Stage stage,
                             String message,
                             String styleClass) {

        Label label = new Label(message);

        label.getStyleClass().add("toast");
        label.getStyleClass().add(styleClass);

        StackPane root = new StackPane(label);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);

        root.getStylesheets().add(
                Objects.requireNonNull(
                        Toast.class.getResource(
                                Styles.TOAST
                        )
                ).toExternalForm()
        );

        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.setAutoFix(true);
        popup.setAutoHide(true);

        popup.show(stage);

        // Position top center
        Scene scene = stage.getScene();

        popup.setX(
                stage.getX()
                        + (scene.getWidth() / 2)
                        - (label.getWidth() / 2)
        );

        popup.setY(
                stage.getY()
                        + 32
        );

        root.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(FADE_IN, root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(DISPLAY);

        FadeTransition fadeOut = new FadeTransition(FADE_OUT, root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> popup.hide());

        SequentialTransition animation =
                new SequentialTransition(
                        fadeIn,
                        pause,
                        fadeOut
                );

        animation.play();
    }

}