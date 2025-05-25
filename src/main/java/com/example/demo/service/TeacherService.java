package com.example.demo.service;

import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher getById(Long id) {
        return teacherRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Teacher teacher = getById(id);
        teacher.setArchived(true);
        teacherRepository.save(teacher);
    }

    public Teacher update(Long id, Teacher updatedTeacher) {
        Teacher teacher = getById(id);
        teacher.setFirstName(updatedTeacher.getFirstName());
        teacher.setLastName(updatedTeacher.getLastName());
        return teacherRepository.save(teacher);
    }
}
