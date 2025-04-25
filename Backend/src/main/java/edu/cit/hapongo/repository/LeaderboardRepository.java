package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboards, Long> {
    Optional<Leaderboards> findByUserAndLesson(User user, Lesson lesson);
    List<Leaderboards> findByUser(User user);
    List<Leaderboards> findByLesson(Lesson lesson);
    List<Leaderboards> findTop10ByOrderByPointsDesc();
}
