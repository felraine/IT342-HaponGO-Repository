package edu.cit.hapongo.service;

import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.model.UserLesson;
import edu.cit.hapongo.repository.LessonRepository;
import edu.cit.hapongo.repository.UserLessonRepository;
import edu.cit.hapongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLessonService {

    @Autowired
    private UserLessonRepository userLessonRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;  // To fetch User

    // Assign the first lesson to a user (this can be used when a user registers or first starts)
    public UserLesson assignFirstLessonToUser(int userId) {
        Optional<User> userOpt = userRepository.findById(userId);  // Fetch the user by ID
        if (userOpt.isPresent()) {
            List<Lesson> lessons = lessonRepository.findAll();
            if (!lessons.isEmpty()) {
                Lesson firstLesson = lessons.get(0);  // You could also choose which lesson to start with
                UserLesson userLesson = new UserLesson(userOpt.get(), firstLesson);
                userLesson.setActive(true);  // Set the first lesson as active
                return userLessonRepository.save(userLesson);
            }
        }
        return null;
    }

    // Get all lessons assigned to a user
    public List<UserLesson> getUserLessons(int userId) {
        return userLessonRepository.findByUser_UserId(userId);
    }

    // Get the active lesson for a user
    public Optional<UserLesson> getActiveLessonForUser(int userId) {
        List<UserLesson> userLessons = userLessonRepository.findByUser_UserId(userId);
        for (UserLesson userLesson : userLessons) {
            if (userLesson.isActive()) {
                return Optional.of(userLesson);
            }
        }
        return Optional.empty();  // No active lesson found
    }

    // Move the user to the next lesson after completing the current one
    public UserLesson moveToNextLesson(int userId) {
        // Get the current active lesson
        Optional<UserLesson> activeUserLessonOpt = getActiveLessonForUser(userId);
        if (activeUserLessonOpt.isPresent()) {
            UserLesson currentUserLesson = activeUserLessonOpt.get();
            if (currentUserLesson.isCompleted()) {
                // Get the next lesson (assuming lessons are ordered by their IDs)
                Optional<Lesson> nextLessonOpt = lessonRepository.findById(currentUserLesson.getLesson().getLessonId() + 1);
                if (nextLessonOpt.isPresent()) {
                    Lesson nextLesson = nextLessonOpt.get();
                    // Deactivate the current lesson and activate the next one
                    currentUserLesson.setActive(false);
                    userLessonRepository.save(currentUserLesson);

                    // Create a new UserLesson for the next lesson and set it as active
                    UserLesson nextUserLesson = new UserLesson(currentUserLesson.getUser(), nextLesson);
                    nextUserLesson.setActive(true);
                    return userLessonRepository.save(nextUserLesson);
                }
            }
        }
        return null;
    }

    // Mark a lesson as completed for a user
    public UserLesson markLessonAsCompleted(int userId, int lessonId) {
        Optional<UserLesson> userLessonOpt = userLessonRepository.findByUser_UserIdAndLesson_LessonId(userId, lessonId);
        if (userLessonOpt.isPresent()) {
            UserLesson userLesson = userLessonOpt.get();
            userLesson.setCompleted(true);
            return userLessonRepository.save(userLesson);
        }
        return null;  // Return null if the UserLesson is not found
    }
}
