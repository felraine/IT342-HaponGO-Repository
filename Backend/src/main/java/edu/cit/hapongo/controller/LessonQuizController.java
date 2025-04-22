package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.LessonQuiz;
import edu.cit.hapongo.service.LessonQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<LessonQuiz> getQuizzesByLessonId(@PathVariable long lessonId) {
        return lessonQuizService.getQuizzesByLessonId(lessonId);
    }

    // Get a quiz by ID
    @GetMapping("/{questionId}")
    public ResponseEntity<LessonQuiz> getQuizById(@PathVariable long questionId) {
        try {
            LessonQuiz quiz = lessonQuizService.getQuizById(questionId);
            return ResponseEntity.ok(quiz);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new quiz
    @PostMapping
    public ResponseEntity<LessonQuiz> createQuiz(@RequestBody LessonQuiz lessonQuiz) {
        LessonQuiz createdQuiz = lessonQuizService.addQuiz(lessonQuiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    // Update an existing quiz
    @PutMapping("/{questionId}")
    public ResponseEntity<LessonQuiz> updateQuiz(@PathVariable long questionId, @RequestBody LessonQuiz lessonQuizDetails) {
        try {
            LessonQuiz updatedQuiz = lessonQuizService.updateQuiz(questionId, lessonQuizDetails);
            return ResponseEntity.ok(updatedQuiz);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a quiz by ID
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable long questionId) {
        try {
            lessonQuizService.deleteQuiz(questionId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}