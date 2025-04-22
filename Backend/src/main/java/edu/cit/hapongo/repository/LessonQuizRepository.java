package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.LessonQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonQuizRepository extends JpaRepository<LessonQuiz, Long> {
    List<LessonQuiz> findByLesson_LessonId(long lessonId);
}
