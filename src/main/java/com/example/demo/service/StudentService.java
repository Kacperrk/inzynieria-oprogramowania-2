package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student getById(Long id) {
        return studentRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public List<Student> getAll() {
        return studentRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Student student = getById(id);
        student.setArchived(true);
        studentRepository.save(student);
    }

    public Student update(Long id, Student updatedStudent) {
        Student student = getById(id);
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setGroup(updatedStudent.getGroup());
        return studentRepository.save(student);
    }
}
