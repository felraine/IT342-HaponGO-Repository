package edu.cit.hapongo.service;

import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.repository.LeaderboardRepository;
import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Transactional
    public Leaderboards addOrUpdatePoints(User user, Lesson lesson, int points) {
        Optional<Leaderboards> existingEntry = leaderboardRepository.findByUserAndLesson(user, lesson);

        if (existingEntry.isPresent()) {
            Leaderboards leaderboard = existingEntry.get();
            leaderboard.setPoints(points);
            leaderboard.setUpdatedAt(LocalDateTime.now());
            return leaderboardRepository.save(leaderboard);
        } else {
            Leaderboards leaderboard = new Leaderboards(user, lesson, points, LocalDateTime.now());
            return leaderboardRepository.save(leaderboard);
        }
    }

    public Optional<Leaderboards> getLeaderboardByUserAndLesson(User user, Lesson lesson) {
        return leaderboardRepository.findByUserAndLesson(user, lesson);
    }

    public List<Leaderboards> getLeaderboardsForUser(User user) {
        return leaderboardRepository.findByUser(user);
    }

    public List<Leaderboards> getTopLeaderboardForLesson(Lesson lesson) {
        return leaderboardRepository.findByLesson(lesson);
    }

    public List<Leaderboards> getTop10LeaderboardOverall() {
        return leaderboardRepository.findTop10ByOrderByPointsDesc();
    }
}
