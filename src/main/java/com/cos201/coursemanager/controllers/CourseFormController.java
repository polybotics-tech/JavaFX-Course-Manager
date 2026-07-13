package com.cos201.coursemanager.controllers;

import com.cos201.coursemanager.constants.Styles;
import com.cos201.coursemanager.constants.Titles;
import com.cos201.coursemanager.constants.Views;
import com.cos201.coursemanager.models.Course;
import com.cos201.coursemanager.services.CourseService;
import com.cos201.coursemanager.utils.Dialogue;
import com.cos201.coursemanager.utils.SceneManager;
import com.cos201.coursemanager.utils.Toast;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * Controller for the course form view (add/edit course).
 */
public class CourseFormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField codeField;

    @FXML
    private TextField unitField;

    @FXML
    private Label titleLabel;

    @FXML
    private Button saveButton;

    private final CourseService courseService = new CourseService();
    private Course courseToEdit; // If editing an existing course
    private boolean isEditMode = false;

    /**
     * Initializes the controller. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Course selectedCourse = courseService.getSelectedCourse();

        loadActionMode(selectedCourse);

        // Format unitField to allow only numbers
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });

        unitField.setTextFormatter(formatter);
    }

    /**
     * Returns current window's stage
     */
    private Stage getCurrentStage() {
        return (Stage) titleLabel.getScene().getWindow();
    }

    /**
     * Decides what mode the form operates as
     *
     * @param selectedCourse the selected course if in edit mode
     */
    private void loadActionMode(Course selectedCourse) {

        this.courseToEdit = selectedCourse;
        this.isEditMode = (selectedCourse != null);

        if(selectedCourse != null) {
            titleField.setText(selectedCourse.getTitle());
            codeField.setText(selectedCourse.getCode());
            unitField.setText(String.valueOf(selectedCourse.getUnit()));
            titleLabel.setText("Edit Course - " + selectedCourse.getCode());
            saveButton.setText("Save Changes");
        } else {
            titleLabel.setText("New course");
            saveButton.setText("Add Course");

        }
    }

    /**
     * Handles the save button click event.
     */
    @FXML
    private void handleSaveButtonAction() {
        Stage stage = getCurrentStage();

        String title = titleField.getText().trim();
        String code = codeField.getText().trim();
        String unitText = unitField.getText().trim();

        if (code.isEmpty() || title.isEmpty() || unitText.isEmpty()) {
            Toast.error(stage,"Please fill in all fields.");
            return;
        }

        int unit;
        try {
            unit = Integer.parseInt(unitText);
            if (unit < 1 || unit > 6) {
                Toast.error(stage,"Course units must be between 1 and 6.");
                return;
            }
        } catch (NumberFormatException e) {
            Toast.error(stage,"Course units must be a number.");
            return;
        }

        Course course = new Course(code, title, unit);

        try {
            boolean success;
            if (isEditMode) {
                // Update existing course
                success = courseService.updateCourse(courseToEdit.getCode(), course);
                if (success) {
                    Toast.success(stage,"Course updated successfully.");
                } else {
                    Toast.error(stage,"Failed to update course.");
                }
            } else {
                // Add new course
                success = courseService.addCourse(course);
                if (success) {
                    Toast.success(stage,"Course added successfully.");
                } else {
                    Toast.error(stage,"Failed to add course.");
                }
            }

            if (success) {
                clearInputs();
                navigateToDashboard();
            }
        } catch (Exception e) {
            Dialogue.error("Something went wrong.", e);
        }
    }

    /**
     * Handles the 'go back to dashboard' button click event.
     */
    @FXML
    private void handleBackButtonAction() {
        navigateToDashboard();
    }

    /**
     * Clears all existing input values.
     */
    private void clearInputs() {
        titleField.clear();
        codeField.clear();
        unitField.clear();
    }

    /**
     * Navigates back to the course list view.
     */
    private void navigateToDashboard() {
        Stage stage = getCurrentStage();

        courseService.setSelectedCourse(null);

        SceneManager.loadScene(stage, Views.DASHBOARD, Styles.DASHBOARD, Titles.DASHBOARD);
    }
}