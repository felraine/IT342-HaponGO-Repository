package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.repository.projections.UserTotalPoints;
import edu.cit.hapongo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboards, Long> {
    Optional<Leaderboards> findByUserAndLesson(User user, Lesson lesson);
    List<Leaderboards> findByUser(User user);
    List<Leaderboards> findByLesson(Lesson lesson);
    List<Leaderboards> findTop10ByOrderByPointsDesc();


    @Query(value = "SELECT u.name AS name, SUM(l.points) AS totalPoints " +
                   "FROM leaderboards l JOIN users u ON l.user_id = u.user_id " +
                   "WHERE u.user_id = :userId " +
                   "GROUP BY u.user_id, u.name", nativeQuery = true)
    List<UserTotalPoints> findUserTotalPoints(@Param("userId") Long userId);
}
