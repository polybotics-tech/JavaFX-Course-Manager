package com.cos201.coursemanager.utils;

import com.cos201.coursemanager.constants.AppInfos;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    private SceneManager() {}

    public static void loadScene(
            Stage stage,
            String fxml,
            String css,
            String title
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource(fxml)
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            if (css != null) {
                URL cssUrl = SceneManager.class.getResource(css);

                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                } else {
                    System.out.println("CSS file not found: " + css);
                }
            }

            FontLoader.load();

            Image icon = new Image(AppInfos.ICON);
            stage.getIcons().add(icon);

            stage.setWidth(AppInfos.WINDOW_WIDTH);
            stage.setHeight(AppInfos.WINDOW_HEIGHT);
            stage.setResizable(AppInfos.RESIZABLE);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Dialogue.error("Failed to launch screen: " + title, e);
        }


    }
}
