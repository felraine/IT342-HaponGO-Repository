package edu.cit.hapongo.service;

import edu.cit.hapongo.model.LessonQuiz;
import edu.cit.hapongo.repository.LessonQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonQuizService {

    @Autowired
    private LessonQuizRepository lessonQuizRepository;

    // Get all quizzes
    public List<LessonQuiz> getAllQuizzes() {
        return lessonQuizRepository.findAll();
    }

    // Get quizzes by lesson ID
    public List<LessonQuiz> getQuizzesByLessonId(int lessonId) {
        return lessonQuizRepository.findByLesson_LessonId(lessonId);
    }

    // Get a quiz by ID
    public Optional<LessonQuiz> getQuizById(int questionId) {
        return lessonQuizRepository.findById(questionId);
    }

    // Create a new quiz
    public LessonQuiz createQuiz(LessonQuiz lessonQuiz) {
        return lessonQuizRepository.save(lessonQuiz);
    }

    // Update an existing quiz
    public LessonQuiz updateQuiz(int questionId, LessonQuiz lessonQuizDetails) {
        Optional<LessonQuiz> lessonQuizOptional = lessonQuizRepository.findById(questionId);
        if (lessonQuizOptional.isPresent()) {
            LessonQuiz existingQuiz = lessonQuizOptional.get();
            existingQuiz.setQuestion(lessonQuizDetails.getQuestion());
            existingQuiz.setChoice1(lessonQuizDetails.getChoice1());
            existingQuiz.setChoice2(lessonQuizDetails.getChoice2());
            existingQuiz.setChoice3(lessonQuizDetails.getChoice3());
            existingQuiz.setChoice4(lessonQuizDetails.getChoice4());
            existingQuiz.setAnswer(lessonQuizDetails.getAnswer());
            return lessonQuizRepository.save(existingQuiz);
        } else {
            return null; // Handle quiz not found, return null or throw exception
        }
    }

    // Delete a quiz by ID
    public void deleteQuiz(int questionId) {
        lessonQuizRepository.deleteById(questionId);
    }
}
