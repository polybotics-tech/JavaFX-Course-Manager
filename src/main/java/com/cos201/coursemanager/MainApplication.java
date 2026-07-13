package com.cos201.coursemanager;

import com.cos201.coursemanager.constants.Styles;
import com.cos201.coursemanager.constants.Titles;
import com.cos201.coursemanager.constants.Views;
import com.cos201.coursemanager.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.loadScene(
                stage,
                Views.LOGIN,
                Styles.AUTH,
                Titles.LOGIN
        );
    }

    public static void main(String[] args) {
        launch();
    }
}