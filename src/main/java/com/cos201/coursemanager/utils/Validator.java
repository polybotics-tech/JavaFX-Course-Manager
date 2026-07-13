package com.cos201.coursemanager.utils;

import java.util.regex.Pattern;

/**
 * Utility class for validating user input.
 */
public class Validator {

    private Validator() {}

    /* Reusable Regex Patterns */
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("[A-Za-z0-9_]+");
    private static final Pattern FULLNAME_PATTERN =
            Pattern.compile("[A-Za-z ]+");
    private static final Pattern COURSECODE_PATTERN =
            Pattern.compile("[A-Za-z]{2,4}[0-9]{3,4}");

    /**
     * Validates a fullname.
     * Full name must be 3-20 alphabetic characters only.
     *
     * @param fullname the full name to validate
     * @throws IllegalArgumentException if the full name is invalid
     */
    public static void validateFullname(String fullname) {
        fullname = fullname == null ? null : fullname.trim();

        if (fullname == null || fullname.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }

        if (fullname.length() < 3 || fullname.length() > 20) {
            throw new IllegalArgumentException("Full name must be between 3 and 20 characters");
        }
        if (!FULLNAME_PATTERN.matcher(fullname).matches()) {
            throw new IllegalArgumentException("Full name can only contain letters");
        }
    }

    /**
     * Validates a username.
     * Username must be 3-20 characters, alphanumeric and underscores only.
     *
     * @param username the username to validate
     * @throws IllegalArgumentException if the username is invalid
     */
    public static void validateUsername(String username) {
        username = username == null ? null : username.trim();

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
        }
    }

    /**
     * Validates a password.
     * Password must be at least 8 characters, contain at least one letter and one number.
     *
     * @param password the password to validate
     * @throws IllegalArgumentException if the password is invalid
     */
    public static void validatePassword(String password) {
        password = password == null ? null : password.trim();

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
    }

    /**
     * Validates a course code.
     * Course code must be in the format: 2-4 uppercase letters followed by 3-4 digits (e.g., CS101, MATH2201).
     *
     * @param courseCode the course code to validate
     * @throws IllegalArgumentException if the course code is invalid
     */
    public static void validateCourseCode(String courseCode) {
        courseCode = courseCode == null ? null : courseCode.trim().toUpperCase();

        if (courseCode == null || courseCode.isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (!COURSECODE_PATTERN.matcher(courseCode).matches()) {
            throw new IllegalArgumentException("Course code must be 2-4 letters followed by 3-4 digits (e.g., COS101, MATH2201)");
        }
    }

    /**
     * Validates a course title.
     * Course title must be between 3 and 100 characters.
     *
     * @param courseTitle the course title to validate
     * @throws IllegalArgumentException if the course title is invalid
     */
    public static void validateCourseTitle(String courseTitle) {
        courseTitle = courseTitle == null ? null : courseTitle.trim();

        if (courseTitle == null || courseTitle.isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be null or empty");
        }
        if (courseTitle.length() < 3 || courseTitle.length() > 100) {
            throw new IllegalArgumentException("Course title must be between 3 and 100 characters");
        }
    }

    /**
     * Validates course units (credits).
     * Course units must be between 1 and 6.
     *
     * @param units the course units to validate
     * @throws IllegalArgumentException if the course units are invalid
     */
    public static void validateCourseUnit(int units) {
        if (units < 1 || units > 6) {
            throw new IllegalArgumentException("Course units must be between 1 and 6");
        }
    }
}