package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.UserLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, Integer> {

    // Get a list of UserLesson by userId from the User entity
    List<UserLesson> findByUser_UserId(int userId);

    // Find a specific UserLesson by userId and lessonId
    Optional<UserLesson> findByUser_UserIdAndLesson_LessonId(int userId, int lessonId);
}


