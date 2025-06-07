package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ApplicationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "processed_date")
    private LocalDateTime processedDate;

    @Column(name = "response")
    private String response;

    @Column(name = "archived", nullable = false)
    private boolean archived = false;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        logApplicationState(); // nic nie zmienia, ale może być użyteczne w przyszłości
    }

    // Placeholder pod przyszłe logowanie, obecnie nic nie zmienia
    private void logApplicationState() {
        // Możliwość logowania informacji o stanie aplikacji
    }
}
