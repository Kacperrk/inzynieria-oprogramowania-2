package com.example.demo.gui.controller;

import com.example.demo.gui.HttpClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.gui.model.Student;
import com.example.demo.gui.model.Group;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class StudentController {

    @FXML private ListView<String> studentListView;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Long DEFAULT_GROUP_ID = 1L;

    @FXML
    public void initialize() {
        refreshStudentList();
    }

    private void refreshStudentList() {
        try {
            String response = HttpClientService.get("/api/students");
            List<Student> students = objectMapper.readValue(response, new TypeReference<>() {});
            studentListView.getItems().clear();

            for (Student student : students) {
                studentListView.getItems().add(formatStudentName(student));
            }

            logAction("Student list refreshed");

        } catch (Exception e) {
            showError("Błąd ładowania studentów: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddStudent() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            showError("Wprowadź imię i nazwisko");
            return;
        }

        try {
            Student newStudent = createStudent(firstName, lastName);
            String json = objectMapper.writeValueAsString(newStudent);

            HttpClientService.post("/api/students", json);

            clearInputFields();
            refreshStudentList();
            logAction("New student added: " + firstName + " " + lastName);

        } catch (Exception e) {
            showError("Błąd dodawania studenta: " + e.getMessage());
        }
    }

    private Student createStudent(String firstName, String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setArchived(false);

        Group group = new Group();
        group.setId(DEFAULT_GROUP_ID);
        student.setGroup(group);

        return student;
    }

    private void clearInputFields() {
        firstNameField.clear();
        lastNameField.clear();
    }

    private String formatStudentName(Student student) {
        return student.getFirstName() + " " + student.getLastName();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Błąd");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Nic nie zmienia, może służyć do przyszłego logowania
    private void logAction(String info) {
        // Placeholder for future logging
    }
}
