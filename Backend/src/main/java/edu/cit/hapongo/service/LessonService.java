package edu.cit.hapongo.service;

import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    // Create or Update a Lesson
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    // Get a Lesson by its ID
    public Optional<Lesson> getLessonById(long lessonId) {
        return lessonRepository.findById(lessonId);
    }

    // Get all Lessons
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    // Delete a Lesson by its ID
    public void deleteLesson(long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
