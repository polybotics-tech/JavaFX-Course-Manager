package com.cos201.coursemanager.services;

import com.cos201.coursemanager.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling JSON persistence of users.
 * Uses Gson for JSON serialization and deserialization with FileReader/FileWriter.
 */
public class FileService {

    private static final String DATA_DIR = "src/main/resources/com/cos201/coursemanager/data";
    private static final String DATA_FILE = DATA_DIR + "/users.json";

    /**
     * Loads the list of users from the JSON file.
     * If the file does not exist or is empty, returns an empty list.
     *
     * @return list of users
     */
    public List<User> loadUsers() {
        try {
            createDataFileIfMissing();
            File file = new File(DATA_FILE);
            if (file.length() == 0) {
                return new ArrayList<>();
            }

            String json = readFileAsString(file);
            if (json.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> users = gson.fromJson(json, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves the list of users to the JSON file.
     *
     * @param users list of users to save
     */
    public void saveUsers(List<User> users) {
        try {
            createDataFileIfMissing();
            File file = new File(DATA_FILE);

            Gson gson = new Gson();
            String json = gson.toJson(users);

            writeStringToFile(file, json);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Creates the data file and its parent directory if they do not exist.
     *
     * @throws IOException if an I/O error occurs
     */
    private void createDataFileIfMissing() throws IOException {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Reads the entire content of a file as a string.
     *
     * @param file the file to read
     * @return the file content as a string
     * @throws IOException if an I/O error occurs
     */
    private String readFileAsString(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Writes a string to a file.
     *
     * @param file  the file to write to
     * @param data  the string to write
     * @throws IOException if an I/O error occurs
     */
    private void writeStringToFile(File file, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        }
    }
}