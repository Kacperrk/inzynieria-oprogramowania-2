package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public Lesson create(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson getById(Long id) {
        return lessonRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
    }

    public List<Lesson> getAll() {
        return lessonRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Lesson lesson = getById(id);
        lesson.setArchived(true);
        lessonRepository.save(lesson);
    }

    public Lesson update(Long id, Lesson updatedLesson) {
        Lesson lesson = getById(id);
        lesson.setSubject(updatedLesson.getSubject());
        lesson.setTeacher(updatedLesson.getTeacher());
        lesson.setGroup(updatedLesson.getGroup());
        return lessonRepository.save(lesson);
    }
}
