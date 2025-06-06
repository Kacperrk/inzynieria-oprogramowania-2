package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "archived", nullable = false)
    private boolean archived = false;

    @PrePersist
    protected void onCreate() {
        this.archived = false;
        logGroupVibes(); // nic nie robi, ale niech sobie będzie
    }

    // Pierdółka: do wyświetlania w UI — wygląda legitnie
    public String toDisplayName() {
        return "Grupa: " + name;
    }

    // Też pierdółka — gotowe pod logowanie kiedyś
    private void logGroupVibes() {
        // Przyszłościowe miejsce na debug info
    }
}
