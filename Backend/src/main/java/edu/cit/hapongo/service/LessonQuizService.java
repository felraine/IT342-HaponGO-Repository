package edu.cit.hapongo.service;

import edu.cit.hapongo.model.LessonQuiz;
import edu.cit.hapongo.repository.LessonQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonQuizService {

    @Autowired
    private LessonQuizRepository lessonQuizRepository;

    // Get all quizzes
    public List<LessonQuiz> getAllQuizzes() {
        return lessonQuizRepository.findAll();
    }

    // Get quiz by ID
    public LessonQuiz getQuizById(long questionId) {
        return lessonQuizRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("LessonQuiz not found with id: " + questionId));
    }

    // Add new quiz
    public LessonQuiz addQuiz(LessonQuiz lessonQuiz) {
        return lessonQuizRepository.save(lessonQuiz);
    }

    // Update existing quiz
    public LessonQuiz updateQuiz(long questionId, LessonQuiz updatedQuiz) {
        return lessonQuizRepository.findById(questionId).map(existingQuiz -> {
            existingQuiz.setQuestion(updatedQuiz.getQuestion());
            existingQuiz.setChoice1(updatedQuiz.getChoice1());
            existingQuiz.setChoice2(updatedQuiz.getChoice2());
            existingQuiz.setChoice3(updatedQuiz.getChoice3());
            existingQuiz.setChoice4(updatedQuiz.getChoice4());
            existingQuiz.setAnswer(updatedQuiz.getAnswer());
            existingQuiz.setLesson(updatedQuiz.getLesson()); // <-- Add this if you want to allow reassigning the quiz to another lesson
            return lessonQuizRepository.save(existingQuiz);
        }).orElseThrow(() -> new RuntimeException("LessonQuiz not found with id: " + questionId));
    }

    // Delete quiz by ID
    public void deleteQuiz(long questionId) {
        if (!lessonQuizRepository.existsById(questionId)) {
            throw new RuntimeException("LessonQuiz not found with id: " + questionId);
        }
        lessonQuizRepository.deleteById(questionId);
    }

    // Get quizzes by lesson ID
    public List<LessonQuiz> getQuizzesByLessonId(long lessonId) {
        return lessonQuizRepository.findByLesson_LessonId(lessonId);
    }
}
