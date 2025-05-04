package edu.cit.hapongo.service;

import edu.cit.hapongo.dto.OverallLeaderboardProjection;
import edu.cit.hapongo.model.Leaderboards;
import edu.cit.hapongo.repository.LeaderboardRepository;
import edu.cit.hapongo.model.Lesson;
import edu.cit.hapongo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import edu.cit.hapongo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private UserRepository userRepository;

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

    // Aggregating points for each user for a specific lesson
    public List<Leaderboards> getTopLeaderboardForLesson(Lesson lesson) {
        // Fetch all leaderboard entries for the given lesson
        List<Leaderboards> allLeaderboards = leaderboardRepository.findByLesson(lesson);

        // Aggregate points by user (sum points for each user)
        return allLeaderboards.stream()
                .collect(Collectors.groupingBy(
                        leaderboard -> leaderboard.getUser().getUserId(),
                        Collectors.summingInt(Leaderboards::getPoints)
                ))
                .entrySet().stream()
                .map(entry -> {
                    User user = new User();
                    user.setUserId(entry.getKey()); // Rebuild user object
                    return new Leaderboards(user, lesson, entry.getValue(), LocalDateTime.now()); // Create new leaderboard entry
                })
                .sorted((entry1, entry2) -> entry2.getPoints() - entry1.getPoints()) // Sort by points in descending order
                .collect(Collectors.toList());
    }

    public List<Leaderboards> getTop10LeaderboardOverall() {
        return leaderboardRepository.findTop10ByOrderByPointsDesc();
    }

    public List<OverallLeaderboardProjection> getTop10OverallLeaderboard(Pageable pageable) {
        return leaderboardRepository.findTop10OverallLeaderboard(pageable);
    }
}
