package com.example.demo.gui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Group group;
    private boolean archived;
}
