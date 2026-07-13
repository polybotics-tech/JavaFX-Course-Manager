package com.cos201.coursemanager.services;

import com.cos201.coursemanager.models.User;
import com.cos201.coursemanager.session.Session;
import com.cos201.coursemanager.utils.Dialogue;
import com.cos201.coursemanager.utils.Validator;

import java.util.List;

/**
 * Service for authentication operations.
 * Uses JSON file for persistence.
 */
public class AuthService {

    private final FileService fileService;

    public AuthService() {
        this.fileService = new FileService();
    }

    /**
     * Registers a new user.
     *
     * @param fullname the full name to register
     * @param username the username to register
     * @param password the password for the user
     * @return true if registration successful, false if username already exists or validation fails
     */
    public boolean register(String fullname, String username, String password) {
        if (!isValidFullname(fullname) || !isValidUsername(username) || !isValidPassword(password)) {
            return false;
        }

        User user = new User(fullname, username, password);
        List<User> users = fileService.loadUsers();
        users.add(user);
        fileService.saveUsers(users);
        return true;
    }

    /**
     * Logs in a user.
     *
     * @param username the username to login
     * @param password the password for the user
     * @return the logged-in user if successful, null otherwise
     */
    public User login(String username, String password) {
        List<User> users = fileService.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Session.getInstance().setCurrentUser(user);
                return user;
            }
        }
        return null;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        Session.getInstance().clear();
    }

    /**
     * Checks if a username already exists.
     *
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        List<User> users = fileService.loadUsers();
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    /**
     * Validates a name by catching validation exceptions.
     *
     * @param fullname the fullname to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidFullname(String fullname) {
        try {
            Validator.validateFullname(fullname);
            return true;
        } catch (IllegalArgumentException e) {
            String errMsg = e.getMessage();
            Dialogue.error(!errMsg.isEmpty() ? errMsg : "Please provide a valid name");
            return false;
        }
    }

    /**
     * Validates a username by catching validation exceptions.
     *
     * @param username the username to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidUsername(String username) {
        try {
            Validator.validateUsername(username);
            return true;
        } catch (IllegalArgumentException e) {
            String errMsg = e.getMessage();
            Dialogue.error(!errMsg.isEmpty() ? errMsg : "Please provide a valid username");
            return false;
        }
    }

    /**
     * Validates a password by catching validation exceptions.
     *
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidPassword(String password) {
        try {
            Validator.validatePassword(password);
            return true;
        } catch (IllegalArgumentException e) {
            String errMsg = e.getMessage();
            Dialogue.error(!errMsg.isEmpty() ? errMsg : "Please provide a valid password");
            return false;
        }
    }
}