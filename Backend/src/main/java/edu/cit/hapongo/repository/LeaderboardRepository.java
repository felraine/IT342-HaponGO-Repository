package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.Leaderboards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;  

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboards, Long> {

    List<Leaderboards> findTop10ByOrderByPointsDesc();
    Optional<Leaderboards> findByUser_UserIdAndLessonId(int userId, int lessonId);
    List<Leaderboards> findByUser_UserId(int userId);
    List<Leaderboards> findByLessonId(int lessonId);
}
