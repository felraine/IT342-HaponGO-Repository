package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.LessonContent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonContentRepository extends JpaRepository<LessonContent, Integer> {
    List<LessonContent> findByLesson_LessonId(int lessonId);
}
