package com.cos201.coursemanager.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the course management system.
 */
public class User {
    private String fullname;
    private String username;
    private String password;
    private List<Course> courses;

    /**
     * Constructs a new User with the specified credentials.
     *
     * @param fullname the full name
     * @param username the username
     * @param password the password
     */
    public User(String fullname, String username, String password) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.courses = new ArrayList<>();
    }


    public User() {
        this.courses = new ArrayList<>();
    }


    public String getFullname() {
        return fullname;
    }


    public String getUsername() {
        return username;
    }


    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the list of courses.
     *
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses.
     *
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the user's list.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the user's list.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Checks if the user has a specific course.
     *
     * @param course the course to check
     * @return true if the user has the course, false otherwise
     */
    public boolean hasCourse(Course course) {
        return courses.contains(course);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", courses=" + courses +
                '}';
    }
}