package com.cos201.coursemanager.session;

import com.cos201.coursemanager.models.User;

/**
 * Singleton class to manage the current user session.
 */
public class Session {
    // Singleton instance
    private static final Session instance = new Session();

    // Current logged-in user
    private User currentUser;


    private Session() {
    }

    /**
     * Gets the singleton instance.
     *
     * @return the Session instance
     */
    public static Session getInstance() {
        return instance;
    }

    /**
     * Gets the current session user.
     *
     * @return the current user or null if none
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current session user.
     *
     * @param user the user to set as current
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Clears the current user session.
     */
    public void clear() {
        this.currentUser = null;
    }
}