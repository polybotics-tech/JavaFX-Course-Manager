package com.cos201.coursemanager.controllers;

import com.cos201.coursemanager.constants.Styles;
import com.cos201.coursemanager.constants.Titles;
import com.cos201.coursemanager.constants.Views;
import com.cos201.coursemanager.models.Course;
import com.cos201.coursemanager.services.CourseService;
import com.cos201.coursemanager.utils.Dialogue;
import com.cos201.coursemanager.utils.SceneManager;
import com.cos201.coursemanager.utils.Toast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.cos201.coursemanager.models.User;
import com.cos201.coursemanager.session.Session;

import java.util.List;

/**
 * Controller for the dashboard view.
 */
public class DashboardController {

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label numOfCoursesLabel;

    @FXML
    private Label numberOfUnitsLabel;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> codeColumn;

    @FXML
    private TableColumn<Course, String> titleColumn;

    @FXML
    private TableColumn<Course, Integer> unitColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button clearButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private User currentUser;
    private final CourseService courseService = new CourseService();
    private final ObservableList<Course> courseList = FXCollections.observableArrayList();


    /**
     * Initializes the controller. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        User user = Session.getInstance().getCurrentUser();

        boolean isReady = initializeUser(user);

        if(isReady) {
            initializeTable();
            initializeSearch();
            loadCourses();
        }
    }

    /**
     * Initializes current user for dashboard features
     */
    private boolean initializeUser(User user) {
        if (user != null) {
            this.currentUser = user;
            fullnameLabel.setText(user.getFullname());
            usernameLabel.setText("@" + user.getUsername());

            return true;
        }

        // Navigate back to log-in if no session user
        Stage stage = getCurrentStage();

        if(stage != null) {
            SceneManager.loadScene(stage,Views.LOGIN,Styles.AUTH,Titles.LOGIN);
        }

        return false;
    }

    /**
     * Initializes course table and it's event listeners
     */
    private void initializeTable() {
        // Set up table columns
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        // Set up double-click to edit
        courseTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY) && event.getClickCount() == 2) {
                handleEditButtonAction();
            }
        });

        editButton.setVisible(false);
        deleteButton.setVisible(false);
        editButton.managedProperty().bind(editButton.visibleProperty());
        deleteButton.managedProperty().bind(deleteButton.visibleProperty());

        // Disable edit/delete buttons when no selection
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean hasSelection = (newSelection != null);
            editButton.setDisable(!hasSelection);
            deleteButton.setDisable(!hasSelection);
            editButton.setVisible(hasSelection);
            deleteButton.setVisible(hasSelection);
        });
    }

    /***
     * Initializes search components and their events
     */
    private void initializeSearch() {
        clearButton.setVisible(false);
        clearButton.managedProperty().bind(clearButton.visibleProperty());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasContent = !newValue.trim().isEmpty();

            clearButton.setVisible(hasContent);
        });
    }

    /**
     * Loads the courses for the current user.
     */
    private void loadCourses() {
        if (currentUser == null) {
            return;
        }

        List<Course> courses = courseService.getAllCourses();
        courseList.setAll(courses);
        courseTable.setItems(courseList);

        int numOfCourse = courses.size();
        int numOfUnits = courseService.computeTotalUnits();
        numOfCoursesLabel.setText(String.valueOf(numOfCourse));
        numberOfUnitsLabel.setText(String.valueOf(numOfUnits));
    }

    /**
     * Refreshes the course list from the service.
     */
    private void refreshCourseList() {
        if (currentUser != null) {
            List<Course> courses = courseService.getAllCourses();
            courseList.setAll(courses);
            courseTable.setItems(courseList);
        }
    }

    /**
     * Performs a search based on the search field text.
     */
    private void performCourseSearch() {
        if (currentUser == null) {
            return;
        }

        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            refreshCourseList();
            return;
        }

        List<Course> searchResult = courseService.searchCourseRecursive(searchText);
        courseTable.setItems(FXCollections.observableArrayList(searchResult));
    }

    /**
     * Returns current window's stage
     */
    private Stage getCurrentStage() {
        Scene scene = usernameLabel.getScene();
        if(scene != null) {
            return (Stage) usernameLabel.getScene().getWindow();
        }

        return null;
    }

    /**
     * Handles the search button-click/enter-key event.
     */
    @FXML
    private void handleSearchAction() {
        performCourseSearch();
    }

    /**
     * Handles the clear search button click event.
     */
    @FXML
    private void handleClearSearchAction() {
        searchField.clear();
        refreshCourseList();
    }

    /**
     * Handles the add button click event.
     */
    @FXML
    private void handleAddButtonAction() {
        Stage stage = getCurrentStage();

        courseService.setSelectedCourse(null);
        SceneManager.loadScene(stage,Views.COURSE_FORM,Styles.AUTH,Titles.NEW_COURSE);
    }

    /**
     * Handles the edit button click event.
     */
    @FXML
    private void handleEditButtonAction() {
        Stage stage = getCurrentStage();

        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            Toast.warning(stage,"Please select a course to edit.");
            return;
        }

        courseService.setSelectedCourse(selectedCourse);
        SceneManager.loadScene(stage, Views.COURSE_FORM, Styles.AUTH, Titles.EDIT_COURSE);
    }

    /**
     * Handles the delete button click event.
     */
    @FXML
    private void handleDeleteButtonAction() {
        Stage stage = getCurrentStage();

        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            Toast.warning(stage,"Please select a course to delete.");
            return;
        }

        String headerText = "Delete Course: " + selectedCourse.getCode() + " - " + selectedCourse.getTitle();
        boolean confirmAction = Dialogue.confirm("Confirm Delete",headerText,"Are you sure you want to delete this course?");
        if (confirmAction) {
            try {
                boolean deleted = courseService.deleteCourse(selectedCourse.getCode());
                if (deleted) {
                    Toast.success(stage,"Course deleted successfully.");
                    refreshCourseList();
                } else {
                    Toast.error(stage,"Failed to delete course.");
                }
            } catch (Exception e) {
                Dialogue.error("Error deleting selected course", e);
            }
        }
    }
}