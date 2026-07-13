package com.cos201.coursemanager.services;

import com.cos201.coursemanager.models.Course;
import com.cos201.coursemanager.models.User;
import com.cos201.coursemanager.session.Session;
import com.cos201.coursemanager.utils.Dialogue;
import com.cos201.coursemanager.utils.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service for course management operations.
 * Persists data via JSON file (all users with their courses).
 */
public class CourseService {

    private final FileService fileService = new FileService();
    private static Course selectedCourse = null;

    /**
     * Adds a course to the currently logged-in user.
     *
     * @param course the course to add
     * @return true if added successfully, false if validation fails, user not logged in, or course already exists for the user
     */
    public boolean addCourse(Course course) {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        if (course == null || !isValidCourse(course)) {
            return false;
        }
        List<User> users = fileService.loadUsers();
        User userToUpdate = findUserByUsername(users, currentUser.getUsername());
        if (userToUpdate == null) {
            return false;
        }
        if (userToUpdate.getCourses().contains(course)) {
            Dialogue.error("This course already exists in your records.");
            return false;
        }
        userToUpdate.getCourses().add(course);
        fileService.saveUsers(users);
        return true;
    }


    /**
     * Sets the global selected course for editing/deleting.
     *
     * @param course the course to add
     */
    public void setSelectedCourse(Course course) {
        this.selectedCourse = course;
    }

    /**
     * Fetches the global selected course for editing/deleting.
     *
     * @return the selected course
     */
    public Course getSelectedCourse() {
        return this.selectedCourse;
    }

    /**
     * Updates a course for the currently logged-in user.
     *
     * @param courseId the ID of the course to update
     * @param updatedCourse the updated course data
     * @return true if updated successfully, false if validation fails, course not found, or no user logged in
     */
    public boolean updateCourse(String courseId, Course updatedCourse) {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        if (updatedCourse == null || !isValidCourse(updatedCourse)) {
            return false;
        }
        List<User> users = fileService.loadUsers();
        User userToUpdate = findUserByUsername(users, currentUser.getUsername());
        if (userToUpdate == null) {
            return false;
        }
        List<Course> courses = userToUpdate.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course.getCode().equals(courseId)) {
                courses.set(i, updatedCourse);
                fileService.saveUsers(users);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a course by its ID from the currently logged-in user's course list.
     *
     * @param courseId the ID of the course to delete
     * @return true if deleted successfully, false if not found or no user logged in
     */
    public boolean deleteCourse(String courseId) {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        List<User> users = fileService.loadUsers();
        User userToUpdate = findUserByUsername(users, currentUser.getUsername());
        if (userToUpdate == null) {
            return false;
        }
        boolean removed = userToUpdate.getCourses().removeIf(course -> course.getCode().equals(courseId));
        if (removed) {
            fileService.saveUsers(users);
        }
        return removed;
    }

    /**
     * Searches for courses by title (case-insensitive) or course code in the currently logged-in user's course list.
     * Uses recursion to demonstrate the concept.
     *
     * @param searchQuery the string to base search on
     * @return list of matching courses
     */
    public List<Course> searchCourseRecursive(String searchQuery) {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) {
            return List.of();
        }
        List<User> users = fileService.loadUsers();
        User user = findUserByUsername(users, currentUser.getUsername());
        if (user == null) {
            return List.of();
        }
        List<Course> result = new ArrayList<>();
        searchCourseRecursiveHelper(searchQuery, user.getCourses(), 0, result);
        return result;
    }

    /**
     * Helper method for recursive search.
     *
     * @param query the query to search for
     * @param courses the list of courses to search
     * @param index the current index
     * @param result the list to store results
     */
    private void searchCourseRecursiveHelper(String query, List<Course> courses, int index, List<Course> result) {
        if (index >= courses.size()) {
            return;
        }
        Course course = courses.get(index);
        if (course.getTitle().toLowerCase().contains(query.toLowerCase()) || course.getCode().toLowerCase().contains(query)) {
            result.add(course);
        }
        searchCourseRecursiveHelper(query, courses, index + 1, result);
    }

    /**
     * Computes the total units (credits) of all courses for the currently logged-in user.
     *
     * @return the total credits
     */
    public int computeTotalUnits() {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) {
            return 0;
        }
        List<User> users = fileService.loadUsers();
        User user = findUserByUsername(users, currentUser.getUsername());
        if (user == null) {
            return 0;
        }
        return user.getCourses().stream()
                .mapToInt(Course::getUnit)
                .sum();
    }

    /**
     * Gets all courses for the currently logged-in user.
     *
     * @return an unmodifiable list of all courses for the current user
     */
    public List<Course> getAllCourses() {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) {
            return Collections.emptyList();
        }
        List<User> users = fileService.loadUsers();
        User user = findUserByUsername(users, currentUser.getUsername());
        if (user == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(user.getCourses());
    }

    /**
     * Validates a course object.
     *
     * @param course the course to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidCourse(Course course) {
        try {
            Validator.validateCourseCode(course.getCode());
            Validator.validateCourseTitle(course.getTitle());
            Validator.validateCourseUnit(course.getUnit());
            return true;
        } catch (IllegalArgumentException e) {
            String errMsg = e.getMessage();
            Dialogue.error(!errMsg.isEmpty() ? errMsg : "Invalid course information");
            return false;
        }
    }

    /**
     * Finds a user by username in the list.
     *
     * @param users list of users to search
     * @param username the username to find
     * @return the user if found, null otherwise
     */
    private User findUserByUsername(List<User> users, String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}