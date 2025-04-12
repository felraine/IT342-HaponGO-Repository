package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.LessonQuiz;
import edu.cit.hapongo.service.LessonQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lesson-quizzes")
public class LessonQuizController {

    @Autowired
    private LessonQuizService lessonQuizService;

    // Get all quizzes
    @GetMapping
    public List<LessonQuiz> getAllQuizzes() {
        return lessonQuizService.getAllQuizzes();
    }

    // Get quizzes by lesson ID
    @GetMapping("/lesson/{lessonId}")
    public List<LessonQuiz> getQuizzesByLessonId(@PathVariable int lessonId) {
        return lessonQuizService.getQuizzesByLessonId(lessonId);
    }

    // Get a quiz by ID
    @GetMapping("/{questionId}")
    public ResponseEntity<LessonQuiz> getQuizById(@PathVariable int questionId) {
        Optional<LessonQuiz> quiz = lessonQuizService.getQuizById(questionId);
        return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new quiz
    @PostMapping
    public ResponseEntity<LessonQuiz> createQuiz(@RequestBody LessonQuiz lessonQuiz) {
        LessonQuiz createdQuiz = lessonQuizService.createQuiz(lessonQuiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    // Update an existing quiz
    @PutMapping("/{questionId}")
    public ResponseEntity<LessonQuiz> updateQuiz(@PathVariable int questionId, @RequestBody LessonQuiz lessonQuizDetails) {
        LessonQuiz updatedQuiz = lessonQuizService.updateQuiz(questionId, lessonQuizDetails);
        if (updatedQuiz != null) {
            return ResponseEntity.ok(updatedQuiz);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a quiz by ID
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable int questionId) {
        lessonQuizService.deleteQuiz(questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}