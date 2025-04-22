package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.UserLesson;
import edu.cit.hapongo.service.UserLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-lessons")
public class UserLessonController {

    @Autowired
    private UserLessonService userLessonService;

    // Assign the first lesson to a user (e.g., when a user first starts)
    @PostMapping("/assign-first/{userId}")
    public ResponseEntity<UserLesson> assignFirstLessonToUser(@PathVariable long userId) {
        UserLesson userLesson = userLessonService.assignFirstLessonToUser(userId);  // Assign the first lesson using the provided userId
        return userLesson != null ? ResponseEntity.ok(userLesson) : ResponseEntity.badRequest().build();
    }

    // Get all lessons for a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserLesson>> getUserLessons(@PathVariable long userId) {
        List<UserLesson> userLessons = userLessonService.getUserLessons(userId);
        return ResponseEntity.ok(userLessons);
    }

    // Get the active lesson for a specific user
    @GetMapping("/{userId}/active")
    public ResponseEntity<UserLesson> getActiveLessonForUser(@PathVariable long userId) {
        Optional<UserLesson> activeLesson = userLessonService.getActiveLessonForUser(userId);
        return activeLesson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mark a lesson as completed for a user
    @PatchMapping("/{userId}/complete/{lessonId}")
    public ResponseEntity<UserLesson> markLessonAsCompleted(@PathVariable long userId, @PathVariable long lessonId) {
        UserLesson completedLesson = userLessonService.markLessonAsCompleted(userId, lessonId);
        return completedLesson != null ? ResponseEntity.ok(completedLesson) : ResponseEntity.notFound().build();
    }

    // Move the user to the next lesson
    @PatchMapping("/{userId}/next")
    public ResponseEntity<UserLesson> moveToNextLesson(@PathVariable long userId) {
        UserLesson nextLesson = userLessonService.moveToNextLesson(userId);
        return nextLesson != null ? ResponseEntity.ok(nextLesson) : ResponseEntity.notFound().build();
    }
}
