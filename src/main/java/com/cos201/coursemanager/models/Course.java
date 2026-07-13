package com.cos201.coursemanager.models;

import java.util.Objects;

/**
 * Represents a course in the course management system.
 */
public class Course {
    private String code;
    private String title;
    private int unit;

    /**
     * Constructs a new Course with the specified details.
     *
     * @param code the course code (e.g., CS101)
     * @param title the course title
     * @param unit the number of credits
     */
    public Course(String code, String title, int unit) {
        this.code = code;
        this.title = title;
        this.unit = unit;
    }

    /**
     * Default constructor for frameworks.
     */
    public Course() {
    }

    /**
     * Gets the course code.
     *
     * @return the course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code.
     *
     * @param code the course code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the course title.
     *
     * @return the course title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the course title.
     *
     * @param title the course title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the number of credits.
     *
     * @return the number of credits
     */
    public int getUnit() {
        return unit;
    }

    /**
     * Sets the number of credits.
     *
     * @param unit the number of credits to set
     */
    public void setUnit(int unit) {
        this.unit = unit;
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
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", unit=" + unit +
                '}';
    }
}