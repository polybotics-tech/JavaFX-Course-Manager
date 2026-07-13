package com.cos201.coursemanager.controllers;

import com.cos201.coursemanager.constants.Styles;
import com.cos201.coursemanager.constants.Titles;
import com.cos201.coursemanager.constants.Views;
import com.cos201.coursemanager.services.AuthService;
import com.cos201.coursemanager.models.User;
import com.cos201.coursemanager.utils.Dialogue;
import com.cos201.coursemanager.utils.SceneManager;
import com.cos201.coursemanager.utils.Toast;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the login screen.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField fullnameField;


    private final AuthService authService = new AuthService();

    /**
     * Returns current window's stage
     */
    private Stage getCurrentStage() {
        return (Stage) usernameField.getScene().getWindow();
    }


    /**
     * Handles the login button click event.
     */
    @FXML
    private void handleLoginButtonAction() {
        Stage stage = getCurrentStage();

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.error(stage,"Please fill all fields");
            return;
        }

        // Attempt login
        User user = authService.login(username, password);
        if (user != null) {
            Toast.success(stage,"Login successful! Redirecting...");

            // Load dashboard
            SceneManager.loadScene(stage,Views.DASHBOARD,Styles.DASHBOARD,Titles.DASHBOARD);
        } else {
            Toast.error(stage, "Invalid username or password.");
        }
    }

    /**
     * Handles the register button click event.
     */
    @FXML
    private void handleRegisterButtonAction() {
        Stage stage = getCurrentStage();

        String fullname = fullnameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.error(stage,"Please fill all fields.");
            return;
        }

        if(authService.usernameExists(username)) {
            Toast.error(stage, "Username already in use");
            return;
        }

        // Attempt registration
        boolean isRegistered = authService.register(fullname, username, password);
        if (isRegistered) {
            Toast.success(stage,"Registration successful. You can now log in.");
            clearInputs();

            SceneManager.loadScene(stage, Views.LOGIN, Styles.AUTH, Titles.LOGIN);
        } else {
            Toast.error(stage, "Registration failed. Please try again");
        }
    }

    /**
     * Handles navigating to 'register' screen.
     */
    @FXML
    private void handleSwitchToRegisterScreenAction() {
        clearInputs();

        Stage stage = getCurrentStage();

        SceneManager.loadScene(stage,Views.REGISTER,Styles.AUTH,Titles.REGISTER);
    }

    /**
     * Handles navigating to 'login' screen.
     */
    @FXML
    private void handleSwitchToLoginScreenAction() {
        clearInputs();

        Stage stage = getCurrentStage();

        SceneManager.loadScene(stage,Views.LOGIN,Styles.AUTH,Titles.LOGIN);

    }

    /**
     * Clears all existing input value
     */
    private void clearInputs() {
        if(fullnameField != null) {
            fullnameField.clear();
        }

        usernameField.clear();
        passwordField.clear();
    }
}