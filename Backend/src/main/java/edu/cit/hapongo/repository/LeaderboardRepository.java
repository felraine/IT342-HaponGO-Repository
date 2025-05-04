package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.model.User;
import edu.cit.hapongo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.cit.hapongo.dto.OverallLeaderboardProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboards, Long> {
    Optional<Leaderboards> findByUserAndLesson(User user, Lesson lesson);
    List<Leaderboards> findByUser(User user);
    List<Leaderboards> findByLesson(Lesson lesson);
    List<Leaderboards> findTop10ByOrderByPointsDesc();

    @Query("SELECT lb.user.userId AS userId, lb.user.name AS userName, SUM(lb.points) AS totalPoints " +
       "FROM Leaderboards lb " +
       "GROUP BY lb.user.userId, lb.user.name " +
       "ORDER BY totalPoints DESC")
    List<OverallLeaderboardProjection> findTop10OverallLeaderboard(Pageable pageable);
}
