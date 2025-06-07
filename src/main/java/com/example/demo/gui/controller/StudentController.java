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

    @FXML
    public void initialize() {
        loadStudents();
    }

    private void loadStudents() {
        try {
            String response = HttpClientService.get("/api/students");
            List<Student> students = objectMapper.readValue(response, new TypeReference<>() {});
            studentListView.getItems().clear();
            for (Student s : students) {
                studentListView.getItems().add(s.getFirstName() + " " + s.getLastName());
            }
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
            Student newStudent = new Student();
            newStudent.setFirstName(firstName);
            newStudent.setLastName(lastName);

            newStudent.setArchived(false);

            Group group = new Group();
            group.setId(1L);
            newStudent.setGroup(group);

            String json = objectMapper.writeValueAsString(newStudent);
            HttpClientService.post("/api/students", json);

            firstNameField.clear();
            lastNameField.clear();
            loadStudents();
        } catch (Exception e) {
            showError("Błąd dodawania studenta: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Błąd");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
