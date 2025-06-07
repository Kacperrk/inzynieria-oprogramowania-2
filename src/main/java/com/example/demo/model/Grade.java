package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @Column(name = "value", nullable = false)
    private GradeValue value;

    @Column(name = "comment")
    private String comment;

    @Column(name = "archived", nullable = false)
    private boolean archived = false;

    @PrePersist
    protected void prePersist() {
        this.archived = false; // Upewniamy się, że archived ma zawsze wartość false przy tworzeniu
        logGradeState();       // Nic nie zmienia, ale można logować np. wartości
    }

    // Placeholder — nic nie zmienia, ale gotowe do debugowania
    private void logGradeState() {
        // Przykład: log.info("Saving grade for student ID: " + student.getId());
    }
}
