package com.example.demo.service;

import com.example.demo.model.Grade;
import com.example.demo.model.GradeValue;
import com.example.demo.repository.GradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    public Grade create(Grade grade) {
        validateValue(grade.getValue());

        return gradeRepository.save(grade);
    }

    public Grade getById(Long id) {
        return gradeRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found"));
    }

    public List<Grade> getAll() {
        return gradeRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Grade grade = getById(id);
        grade.setArchived(true);
        gradeRepository.save(grade);
    }

    public Grade update(Long id, Grade updatedGrade) {
        validateValue(updatedGrade.getValue());

        Grade grade = getById(id);
        grade.setStudent(updatedGrade.getStudent());
        grade.setLesson(updatedGrade.getLesson());
        grade.setValue(updatedGrade.getValue());
        grade.setComment(updatedGrade.getComment());
        return gradeRepository.save(grade);
    }

    private void validateValue(GradeValue value) {
        if (value == null || !EnumSet.allOf(GradeValue.class).contains(value)) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }
    }
}
