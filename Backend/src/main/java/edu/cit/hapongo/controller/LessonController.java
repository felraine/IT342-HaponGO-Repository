package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    // Create or Update a Lesson
    @PostMapping("/save")
    public ResponseEntity<Lesson> saveLesson(@RequestBody Lesson lesson) {
        Lesson savedLesson = lessonService.saveLesson(lesson);
        return ResponseEntity.ok(savedLesson);
    }

    // Get all Lessons
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons);
    }

    // Get a Lesson by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
        Optional<Lesson> lesson = lessonService.getLessonById(id);
        return lesson.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Lesson by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    // Update a Lesson by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable int id, @RequestBody Lesson lesson) {
        Optional<Lesson> existingLesson = lessonService.getLessonById(id);
        if (existingLesson.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Update the existing lesson
        lesson.setLessonId(id);  // Ensure the ID is the same for the update
        Lesson updatedLesson = lessonService.saveLesson(lesson); // Save the updated lesson

        return ResponseEntity.ok(updatedLesson);
    }
}
